package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.client.dto.OidcPublicKeys
import dnd11th.blooming.client.oauth.AppleOauthClient
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.domain.entity.user.OidcUser
import org.springframework.stereotype.Component

@Component
class AppleIdTokenResolver(
    private val appleOauthProperties: AppleOauthProperties,
    private val appleOauthClient: AppleOauthClient,
    private val idTokenProcessor: IdTokenProcessor,
) : OpenIdTokenResolver {
    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = appleOauthClient.getPublicKeys()
        try {
            return idTokenProcessor.process(
                idToken,
                oidcPublicKeys,
                appleOauthProperties.iss,
                appleOauthProperties.aud,
            )
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
        }
    }
}
