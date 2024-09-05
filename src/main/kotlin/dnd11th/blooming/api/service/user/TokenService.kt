package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.common.holder.InstantHolder
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import dnd11th.blooming.domain.entity.user.UserClaims
import dnd11th.blooming.domain.repository.token.BlackListRepository
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtProvider: JwtProvider,
    private val blackListRepository: BlackListRepository,
    private val instantHolder: InstantHolder,
) {
    fun reissueToken(oldToken: String): TokenResponse {
        val userClaims = jwtProvider.resolveRefreshToken(oldToken)
        if (blackListRepository.existsByToken(oldToken)) {
            throw UnAuthorizedException(ErrorType.INVALID_REFRESH_TOKEN)
        }
        saveBlackList(oldToken, userClaims)

        val accessToken = jwtProvider.generateAccessToken(userClaims.id, userClaims.email)
        val expireIn = jwtProvider.getExpiredIn()
        val refreshToken = jwtProvider.generateRefreshToken(userClaims.id, userClaims.email)
        val refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn()

        return TokenResponse(
            accessToken,
            expireIn,
            refreshToken,
            refreshTokenExpiresIn,
        )
    }

    private fun saveBlackList(
        oldToken: String,
        userClaims: UserClaims,
    ) {
        blackListRepository.save(
            RefreshToken.create(
                oldToken,
                userClaims.id,
                instantHolder.now(),
                jwtProvider.getRefreshTokenExpiration(oldToken),
            ),
        )
    }
}
