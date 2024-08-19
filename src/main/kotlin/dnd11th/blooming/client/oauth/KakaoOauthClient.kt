package dnd11th.blooming.client.oauth

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "KakaoOauthClient",
    url = "https://kauth.kakao.com",
)
interface KakaoOauthClient {
    @GetMapping("/.well-known/jwks.json")
    fun getPublicKeys(): OidcPublicKeys
}
