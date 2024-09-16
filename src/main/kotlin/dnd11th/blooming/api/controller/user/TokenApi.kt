package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.annotation.ApiErrorResponse
import dnd11th.blooming.api.annotation.ApiErrorResponses
import dnd11th.blooming.api.dto.user.TokenRequest
import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "1. [인증]")
interface TokenApi {
    @Operation(summary = "토큰 재발급 API 입니다.")
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.INVALID_REFRESH_TOKEN, "해당 사용자의 RefreshToken이 존재하지 않을 시 에러입니다."),
            ApiErrorResponse(ErrorType.DUPLICATE_USER_LOGIN, "중복된 로그인 발생 시 에러입니다."),
        ],
    )
    fun reissueToken(tokenRequest: TokenRequest): TokenResponse
}
