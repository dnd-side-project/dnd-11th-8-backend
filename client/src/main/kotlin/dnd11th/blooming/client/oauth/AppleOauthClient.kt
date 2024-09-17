package dnd11th.blooming.client.oauth

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "AppleOauthClient",
    url = "https://appleid.apple.com",
)
interface AppleOauthClient {
    @GetMapping("/auth/keys")
    fun getPublicKeys(): OidcPublicKeys
}
