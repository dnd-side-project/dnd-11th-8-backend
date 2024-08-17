package dnd11th.blooming.api.service.user.oauth.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.oidc.provider.kakao")
data class KakaoOauthProperties(
    val iss: String,
    val aud: String,
)
