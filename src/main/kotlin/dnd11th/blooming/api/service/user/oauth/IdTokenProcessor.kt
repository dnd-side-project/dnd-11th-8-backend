package dnd11th.blooming.api.service.user.oauth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dnd11th.blooming.client.dto.OidcPublicKeys
import dnd11th.blooming.domain.entity.user.OidcUser
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component
import java.util.Base64

@Component
class IdTokenProcessor(
    private val idTokenValidator: IdTokenValidator,
    private val objectMapper: ObjectMapper,
) {
    fun process(
        idToken: String,
        oidcPublicKeys: OidcPublicKeys,
        iss: String,
        aud: String,
    ): OidcUser {
        val splitToken = idToken.split(".")
        val headerJson = base64Decode(splitToken[0])
        val payloadJson = base64Decode(splitToken[1])
        val header = objectMapper.readValue<Map<String, Any>>(headerJson)
        val payload = objectMapper.readValue<Map<String, Any>>(payloadJson)
        idTokenValidator.verifyPayload(payload, iss, aud)
        val claims = idTokenValidator.verifySignature(idToken, header, oidcPublicKeys)
        return getUserClaims(claims)
    }

    private fun getUserClaims(claims: Claims): OidcUser {
        val email = claims["email"].toString()
        return OidcUser(email)
    }

    private fun base64Decode(encodedString: String): String {
        return String(Base64.getUrlDecoder().decode(encodedString))
    }
}
