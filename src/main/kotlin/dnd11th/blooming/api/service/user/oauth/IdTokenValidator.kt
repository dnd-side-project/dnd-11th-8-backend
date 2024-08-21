package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.client.oauth.OidcPublicKeys
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.PublicKey

@Component
class IdTokenValidator(
    private val publicKeyGenerator: PublicKeyGenerator,
) {
    fun verifySignature(
        idToken: String,
        header: Map<String, Any>,
        oidcPublicKeys: OidcPublicKeys,
    ): Claims {
        val publicKey: PublicKey = publicKeyGenerator.generatePublicKey(header, oidcPublicKeys)
        return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(idToken).payload
    }

    fun verifyPayload(
        payload: Map<String, Any>,
        iss: String,
        aud: String,
    ) {
        payload.apply {
            require(iss == this["iss"]) { throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN) }
            require(aud == this["aud"]) { throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN) }
            require((this["exp"] as Number).toLong() >= System.currentTimeMillis() / 1000) {
                throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
            }
        }
    }
}
