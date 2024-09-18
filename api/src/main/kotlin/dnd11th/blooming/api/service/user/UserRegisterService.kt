package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.jwt.JwtProvider
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.core.entity.region.Region
import dnd11th.blooming.core.entity.user.OauthProvider
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.core.entity.user.UserOauthInfo
import dnd11th.blooming.core.entity.user.UserRegisterInfo
import dnd11th.blooming.core.repository.region.RegionRepository
import dnd11th.blooming.core.repository.user.UserOauthRepository
import dnd11th.blooming.core.repository.user.UserRepository
import dnd11th.blooming.redis.entity.refreshtoken.RefreshToken
import dnd11th.blooming.redis.repository.token.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterService(
    private val userRepository: UserRepository,
    private val userOauthRepository: UserOauthRepository,
    private val regionRepository: RegionRepository,
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    @Transactional
    fun registerUser(
        email: String,
        provider: OauthProvider,
        userRegisterInfo: UserRegisterInfo,
    ): TokenResponse {
        val region: Region =
            regionRepository.findById(userRegisterInfo.regionId)
                .orElseThrow { NotFoundException(ErrorType.NOT_FOUND_REGION) }
        val user =
            userRepository.save(
                User.create(email, userRegisterInfo.nickname, userRegisterInfo.alarmTime, region.nx, region.ny),
            )
        userOauthRepository.save(UserOauthInfo(email = email, provider = provider, user = user))

        val refreshToken: String = jwtProvider.generateRefreshToken(user.id, user.email)
        val refreshTokenExpiresIn: Long = jwtProvider.getRefreshExpiredIn()
        refreshTokenRepository.save(RefreshToken.create(user.id!!, refreshToken, refreshTokenExpiresIn))
        return TokenResponse(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = refreshTokenExpiresIn,
        )
    }
}
