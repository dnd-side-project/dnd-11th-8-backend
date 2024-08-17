package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.client.dto.OidcPublicKeys
import dnd11th.blooming.client.oauth.KakaoOauthClient
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.domain.entity.user.OidcUser
import org.springframework.stereotype.Component

@Component
class KakaoIdTokenResolver(
    private val kakaoOauthClient: KakaoOauthClient,
    private val kakaoOauthProperties: KakaoOauthProperties,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {
    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = kakaoOauthClient.getPublicKeys()
        try {
            return idTokenProcessor.process(
                idToken,
                oidcPublicKeys,
                kakaoOauthProperties.iss,
                kakaoOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
        }
    }
}
