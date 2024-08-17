package dnd11th.blooming.api.service.user.oauth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.oidc.provider.apple")
class AppleOauthProperties(
    val iss: String,
    val aud: String,
)
