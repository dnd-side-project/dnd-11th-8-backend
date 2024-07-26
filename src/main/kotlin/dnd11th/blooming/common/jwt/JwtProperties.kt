package dnd11th.blooming.common.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "auth.jwt")
data class JwtProperties(
    val secret: String,
    val expiration: Long,
)
