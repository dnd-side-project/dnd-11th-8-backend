package dnd11th.blooming.client

import dnd11th.blooming.client.dto.OidcPublicKeys
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "KakaoOuthClient",
    url = "https://kauth.kakao.com",
)
interface KakaoOauthClient {
    @GetMapping("/.well-known/jwks.json")
    fun getPublicKeys(): OidcPublicKeys
}
