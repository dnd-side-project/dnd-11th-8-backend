package dnd11th.blooming.api.service.user

import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.service.user.oauth.OpenIdTokenResolverSelector
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.OidcUser
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserOauthInfo
import dnd11th.blooming.domain.repository.token.RefreshTokenRepository
import dnd11th.blooming.domain.repository.user.UserOauthRepository
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
        val openIdTokenResolver = openIdTokenResolverSelector.select(provider)
        val oidcUser: OidcUser = openIdTokenResolver.resolveIdToken(idToken)
        val userOauthInfo: UserOauthInfo =
            userOauthRepository.findByEmailAndProvider(oidcUser.email, provider)
                ?: return SocialLoginResponse.Pending(
                    registerToken = jwtProvider.generateRegisterToken(oidcUser.email, provider),
                )

        val user: User = userOauthInfo.user
        val refreshToken: String = jwtProvider.generateRefreshToken(user.id, user.email)
        val refreshTokenExpiresIn: Long = jwtProvider.getRefreshExpiredIn()
        refreshTokenRepository.save(RefreshToken.create(user.id!!, refreshToken, refreshTokenExpiresIn))

        return SocialLoginResponse.Success(
            accessToken = jwtProvider.generateAccessToken(user.id, user.email),
            expiresIn = jwtProvider.getExpiredIn(),
            refreshToken = refreshToken,
            refreshTokenExpiresIn = refreshTokenExpiresIn,
        )
    }
}
