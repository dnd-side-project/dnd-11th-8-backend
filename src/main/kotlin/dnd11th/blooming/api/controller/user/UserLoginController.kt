package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.IdTokenRequest
import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.service.user.SocialLoginService
import dnd11th.blooming.domain.entity.user.OauthProvider
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/login")
class UserLoginController(
    private val socialLoginService: SocialLoginService,
) {
    @PostMapping("/{provider}")
    fun login(
        @PathVariable provider: String,
        @RequestBody idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse {
        return socialLoginService.socialLogin(OauthProvider.from(provider), idTokenRequest.idToken)
    }
}
