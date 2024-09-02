package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.TokenRequest
import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.service.user.TokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/token")
class TokenController(
    private val tokenService: TokenService,
) : TokenApi {
    @PostMapping
    override fun reissueToken(
        @RequestBody tokenRequest: TokenRequest,
    ): TokenResponse {
        return tokenService.reissueToken(tokenRequest.refreshToken)
    }
}
