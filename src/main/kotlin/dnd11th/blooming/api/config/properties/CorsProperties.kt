package dnd11th.blooming.api.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cors")
data class CorsProperties(
    val allowedOrigin: List<String>,
    val maxAge: Long,
)
