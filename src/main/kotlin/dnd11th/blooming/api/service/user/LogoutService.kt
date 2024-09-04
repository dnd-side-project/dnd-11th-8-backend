package dnd11th.blooming.api.service.user

import dnd11th.blooming.domain.repository.token.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun logout(accessToken: String, refreshToken: String) {

    }
}
