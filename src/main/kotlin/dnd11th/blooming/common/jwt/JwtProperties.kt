package dnd11th.blooming.common.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "auth.jwt")
data class JwtProperties(
    val access: TokenProperties,
    val refresh: TokenProperties,
) {
    data class TokenProperties(
        val secret: String,
        val expiry: Long,
    )
}
