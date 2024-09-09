package dnd11th.blooming.api.service.user

import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.user.UserClaims
import dnd11th.blooming.domain.repository.token.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
) {
    fun logout(refreshToken: String) {
        val claims: UserClaims = jwtProvider.resolveRefreshToken(refreshToken)
        refreshTokenRepository.deleteByUserId(claims.id)
    }
}
