package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.service.user.oauth.OpenIdTokenResolver
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.OidcUser
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import dnd11th.blooming.domain.repository.user.UserOauthRepository
import org.springframework.stereotype.Service

@Service
class SocialLoginService(
    val jwtProvider: JwtProvider,
    val openIdTokenResolver: OpenIdTokenResolver,
    val userOauthRepository: UserOauthRepository,
) {
    fun socialLogin(
        provider: OauthProvider,
        idToken: String,
    ): SocialLoginResponse {
        val oidcUser: OidcUser = openIdTokenResolver.resolveIdToken(idToken)
        val userOauthInfo: UserOauthInfo =
            userOauthRepository.findByEmailAndProvider(oidcUser.email, provider)
                ?: return SocialLoginResponse.Pending(
                    registerToken = jwtProvider.generateRegisterToken(oidcUser.email, provider),
                )

        val user: User = userOauthInfo.user
        return SocialLoginResponse.Success(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email, user.nickname!!),
            refreshToken = jwtProvider.generateRefreshToken(user.id, user.email, user.nickname!!),
        )
    }
}
