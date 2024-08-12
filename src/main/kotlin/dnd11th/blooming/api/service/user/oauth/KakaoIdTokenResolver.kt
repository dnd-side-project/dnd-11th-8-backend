package dnd11th.blooming.api.service.user.oauth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dnd11th.blooming.client.dto.OidcPublicKeys
import dnd11th.blooming.client.kakao.KakaoOauthClient
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.domain.entity.user.OidcUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Component
class KakaoIdTokenResolver(
    private val kakaoOauthClient: KakaoOauthClient,
    private val kakaoOauthProperties: KakaoOauthProperties,
    private val objectMapper: ObjectMapper,
) : OpenIdTokenResolver {
    override fun resolveIdToken(idToken: String): OidcUser {
        val oidcPublicKeys: OidcPublicKeys = kakaoOauthClient.getPublicKeys()
        return try {
            val splitToken = idToken.split(".")
            val headerJson = base64Decode(splitToken[0])
            val payloadJson = base64Decode(splitToken[1])
            val header = objectMapper.readValue<Map<String, Any>>(headerJson)
            val payload = objectMapper.readValue<Map<String, Any>>(payloadJson)
            verifyPayload(payload)
            val kid = header["kid"] as String
            val claims = verifySignature(idToken, kid, oidcPublicKeys)
            return getUserClaims(claims)
        } catch (e: UnAuthorizedException) {
            throw e
        } catch (e: JwtException) {
            throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getUserClaims(claims: Claims): OidcUser {
        val email = claims["email"].toString()
        return OidcUser(email)
    }

    fun verifySignature(
        idToken: String,
        kid: String,
        oidcPublicKeys: OidcPublicKeys,
    ): Claims {
        val publicKey = getPublicKey(kid, oidcPublicKeys)
        return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(idToken).payload
    }

    fun getPublicKey(
        kid: String,
        oidcPublicKeys: OidcPublicKeys,
    ): PublicKey {
        val matchesKey = oidcPublicKeys.getMatchesKey(kid)
        val nBytes = Base64.getUrlDecoder().decode(matchesKey.n)
        val eBytes = Base64.getUrlDecoder().decode(matchesKey.e)

        val publicKeySpec = RSAPublicKeySpec(BigInteger(1, nBytes), BigInteger(1, eBytes))
        val keyFactory = KeyFactory.getInstance("RSA")

        return keyFactory.generatePublic(publicKeySpec)
    }

    fun verifyPayload(payload: Map<String, Any>) {
        payload.apply {
            require(kakaoOauthProperties.iss == this["iss"]) { throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN) }
            require(kakaoOauthProperties.aud == this["aud"]) { throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN) }
            require((this["exp"] as Number).toLong() >= System.currentTimeMillis() / 1000) {
                throw UnAuthorizedException(ErrorType.INVALID_ID_TOKEN)
            }
        }
    }

    fun base64Decode(encodedString: String): String {
        return String(Base64.getUrlDecoder().decode(encodedString))
    }
}
