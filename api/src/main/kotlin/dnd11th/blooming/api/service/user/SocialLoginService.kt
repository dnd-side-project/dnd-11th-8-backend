package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.jwt.JwtProvider
import dnd11th.blooming.api.service.user.oauth.OpenIdTokenResolverSelector
import dnd11th.blooming.core.entity.user.OauthProvider
import dnd11th.blooming.core.entity.user.OidcUser
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.core.repository.user.UserOauthRepository
import dnd11th.blooming.redis.entity.refreshtoken.RefreshToken
import dnd11th.blooming.redis.repository.token.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SocialLoginService(
    val jwtProvider: JwtProvider,
    val openIdTokenResolverSelector: OpenIdTokenResolverSelector,
    val userOauthRepository: UserOauthRepository,
    val refreshTokenRepository: RefreshTokenRepository,
) {
    fun socialLogin(
        provider: OauthProvider,
        idToken: String,
    ): SocialLoginResponse {
        val oidcUser = resolveOidcUser(provider, idToken)

        return userOauthRepository.findByEmailAndProvider(oidcUser.email, provider)
            ?.let { userOauthInfo -> processExistingUser(userOauthInfo.user) }
            ?: createPendingRegistrationResponse(oidcUser.email, provider)
    }

    private fun resolveOidcUser(
        provider: OauthProvider,
        idToken: String,
    ): OidcUser {
        val openIdTokenResolver = openIdTokenResolverSelector.select(provider)
        return openIdTokenResolver.resolveIdToken(idToken)
    }

    private fun processExistingUser(user: User): SocialLoginResponse.Success {
        val refreshToken = generateAndSaveRefreshToken(user)
        return SocialLoginResponse.Success(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn(),
        )
    }

    private fun createPendingRegistrationResponse(
        email: String,
        provider: OauthProvider,
    ): SocialLoginResponse.Pending {
        return SocialLoginResponse.Pending(
            registerToken = jwtProvider.generateRegisterToken(email, provider),
        )
    }

    private fun generateAndSaveRefreshToken(user: User): String {
        val refreshToken = jwtProvider.generateRefreshToken(user.id, user.email)
        val refreshTokenExpiresIn = jwtProvider.getRefreshExpiredIn()
        refreshTokenRepository.save(RefreshToken.create(user.id!!, refreshToken, refreshTokenExpiresIn))
        return refreshToken
    }
}
