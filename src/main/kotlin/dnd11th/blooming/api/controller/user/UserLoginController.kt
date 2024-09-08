package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.IdTokenRequest
import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.dto.user.TokenRequest
import dnd11th.blooming.api.service.user.LogoutService
import dnd11th.blooming.api.service.user.SocialLoginService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserLoginController(
    private val socialLoginService: SocialLoginService,
    private val logoutService: LogoutService,
) : UserLoginApi {
    @PostMapping("/login/{provider}")
    override fun login(
        @PathVariable provider: String,
        @RequestBody idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse {
        return socialLoginService.socialLogin(OauthProvider.from(provider), idTokenRequest.idToken)
    }

    @Secured
    @PostMapping("/logout")
    override fun logout(
        @LoginUser user: User,
        @RequestBody tokenRequest: TokenRequest,
    ): ResponseEntity<Void> {
        logoutService.logout(tokenRequest.refreshToken)
        return ResponseEntity.noContent().build()
    }
}
