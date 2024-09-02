package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.TokenRequest
import dnd11th.blooming.api.dto.user.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "1. [인증]")
interface TokenApi {
    @Operation(summary = "토큰 재발급 API 입니다.")
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    fun reissueToken(tokenRequest: TokenRequest): TokenResponse
}
