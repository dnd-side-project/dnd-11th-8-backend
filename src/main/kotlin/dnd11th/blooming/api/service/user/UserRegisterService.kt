package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.region.Region
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import dnd11th.blooming.domain.entity.user.UserRegisterInfo
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.region.RegionRepository
import dnd11th.blooming.domain.repository.user.UserOauthRepository
import dnd11th.blooming.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterService(
    private val userRepository: UserRepository,
    private val userOauthRepository: UserOauthRepository,
    private val regionRepository: RegionRepository,
    private val locationRepository: LocationRepository,
    private val jwtProvider: JwtProvider,
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

        locationRepository.saveAll(Location.createDefaultLocations(user))

        userOauthRepository.save(UserOauthInfo(email = email, provider = provider, user = user))
        return TokenResponse(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = jwtProvider.generateRefreshToken(user.id, user.email),
            refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn(),
        )
    }
}
