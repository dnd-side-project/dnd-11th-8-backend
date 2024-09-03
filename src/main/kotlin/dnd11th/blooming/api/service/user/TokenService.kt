package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import dnd11th.blooming.domain.repository.refreshtoken.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun reissueToken(oldToken: String): TokenResponse {
        val userClaims = jwtProvider.resolveRefreshToken(oldToken)
        val storedToken: RefreshToken = refreshTokenRepository.findByToken(oldToken)
        if (storedToken.checkInValid()) {
            throw UnAuthorizedException(ErrorType.INVALID_REFRESH_TOKEN)
        }
        val accessToken = jwtProvider.generateAccessToken(userClaims.id, userClaims.email)
        val expireIn = jwtProvider.getExpiredIn()
        val refreshToken = jwtProvider.generateRefreshToken(userClaims.id, userClaims.email)
        val refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn()

        useStoredToken(storedToken)
        refreshTokenRepository.save(RefreshToken.create(refreshToken, userClaims.id, refreshTokenExpiresIn))

        return TokenResponse(
            accessToken,
            expireIn,
            refreshToken,
            refreshTokenExpiresIn,
        )
    }

    private fun useStoredToken(storedToken: RefreshToken) {
        storedToken.use()
        refreshTokenRepository.update(storedToken)
    }
}
