package dnd11th.blooming.api.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.jwt")
data class JwtProperties(
    val access: TokenProperties,
    val refresh: TokenProperties,
) {
    data class TokenProperties(
        val secret: String,
        val expiry: Long,
    )
}
