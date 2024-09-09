package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.IdTokenRequest
import dnd11th.blooming.api.dto.user.SocialLoginResponse
import dnd11th.blooming.api.dto.user.TokenRequest
import dnd11th.blooming.domain.entity.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "1. [인증]")
interface UserLoginApi {
    @Operation(summary = "소셜 로그인 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "소셜 로그인 성공",
                content = [
                    Content(
                        schema = Schema(implementation = SocialLoginResponse.Success::class),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "200(pending)",
                description = "소셜 로그인 보류",
                content = [
                    Content(
                        schema = Schema(implementation = SocialLoginResponse.Pending::class),
                    ),
                ],
            ),
        ],
    )
    fun login(
        @Parameter(name = "provider", description = "소셜 로그인 제공자 설정(kakao, apple)", `in` = ParameterIn.PATH)
        provider: String,
        idTokenRequest: IdTokenRequest,
    ): SocialLoginResponse

    @Operation(summary = "로그아웃 API")
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    fun logout(
        @Schema(hidden = true) user: User,
        tokenRequest: TokenRequest,
    ): ResponseEntity<Void>
}
