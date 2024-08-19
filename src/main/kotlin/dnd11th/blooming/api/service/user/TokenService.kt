package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.common.jwt.JwtProvider
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val jwtProvider: JwtProvider,
) {
    fun reissueToken(refreshToken: String): TokenResponse {
        val userClaims = jwtProvider.resolveRefreshToken(refreshToken)

        return TokenResponse(
            accessToken = jwtProvider.generateAccessToken(userClaims.id, userClaims.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = jwtProvider.generateRefreshToken(userClaims.id, userClaims.email),
            refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn(),
        )
    }
}
