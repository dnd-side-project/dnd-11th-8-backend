package dnd11th.blooming.api.service.user

import dnd11th.blooming.common.holder.InstantHolder
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.token.BlackListRepository
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val blackListRepository: BlackListRepository,
    private val instantHolder: InstantHolder,
    private val jwtProvider: JwtProvider,
) {
    fun logout(
        loginUser: User,
        refreshToken: String,
    ) {
        blackListRepository.save(
            RefreshToken.create(
                refreshToken,
                loginUser.id!!,
                instantHolder.now(),
                jwtProvider.getRefreshTokenExpiration(refreshToken),
            ),
        )
    }
}
