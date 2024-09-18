package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.client.oauth.OidcPublicKeys
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Component
class PublicKeyGenerator {
    fun generatePublicKey(
        header: Map<String, Any>,
        oidcPublicKeys: OidcPublicKeys,
    ): PublicKey {
        val kid = header["kid"] as String
        val alg = header["alg"] as String
        val matchesKey = oidcPublicKeys.getMatchesKey(kid, alg)
        val nBytes = Base64.getUrlDecoder().decode(matchesKey.n)
        val eBytes = Base64.getUrlDecoder().decode(matchesKey.e)

        val publicKeySpec = RSAPublicKeySpec(BigInteger(1, nBytes), BigInteger(1, eBytes))
        val keyFactory = KeyFactory.getInstance(matchesKey.kty)

        return keyFactory.generatePublic(publicKeySpec)
    }
}
