package dnd11th.blooming.client.oauth.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oidc.provider.kakao")
data class KakaoOauthProperties(
    val iss: String,
    val aud: String,
)
