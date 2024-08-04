package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.service.user.oauth.OpenIdTokenResolver
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.OidcUser
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import dnd11th.blooming.domain.repository.user.UserOauthRepository
import dnd11th.blooming.domain.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class SocialLoginService(
    val jwtProvider: JwtProvider,
    val openIdTokenResolver: OpenIdTokenResolver,
    val userRepository: UserRepository,
    val userOauthRepository: UserOauthRepository,
) {
    fun socialLogin(
        provider: OauthProvider,
        idToken: String,
    ): TokenResponse {
        val oidcUser: OidcUser = openIdTokenResolver.resolveIdToken(idToken)
        val user =
            userOauthRepository.findByEmailAndProvider(oidcUser.email, provider)
                ?.user
                ?: userRepository.save(User(oidcUser.email, oidcUser.nickname)).also { savedUser ->
                    userOauthRepository.save(UserOauthInfo(savedUser, oidcUser.email, provider))
                }
        return generateTokenResponse(user)
    }

    private fun generateTokenResponse(user: User): TokenResponse {
        val accessToken = jwtProvider.generateAccessToken(user.id, user.email, user.nickname)
        val refreshToken = jwtProvider.generateAccessToken(user.id, user.email, user.nickname)
        return TokenResponse(accessToken, refreshToken)
    }
}
