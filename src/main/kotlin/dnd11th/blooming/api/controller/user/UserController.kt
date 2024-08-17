package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.dto.user.UserRegisterRequest
import dnd11th.blooming.api.service.user.UserRegisterService
import dnd11th.blooming.common.annotation.PendingUser
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.RegisterClaims
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userRegisterService: UserRegisterService,
) : UserApi {
    @PostMapping("/register")
    override fun register(
        @PendingUser registerClaims: RegisterClaims,
        @RequestBody userRegisterRequest: UserRegisterRequest,
    ): TokenResponse {
        return userRegisterService.registerUser(
            registerClaims.email,
            OauthProvider.from(registerClaims.provider),
            userRegisterRequest.toUserRegisterInfo(),
        )
    }
}
