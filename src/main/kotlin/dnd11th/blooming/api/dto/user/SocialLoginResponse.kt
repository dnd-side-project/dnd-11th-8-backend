package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

sealed class SocialLoginResponse {
    data class Success(
        @field:Schema(description = "소셜 로그인 성공 여부", example = "success")
        val status: String = "success",
        @field:Schema(description = "accessToken", example = "accessToken")
        val accessToken: String,
        @field:Schema(description = "accessToken 만료일자(초)", example = "604800(1주일)")
        val expiresIn: Int,
        @field:Schema(description = "refreshToken", example = "refreshToken")
        val refreshToken: String,
        @field:Schema(description = "refreshToken 만료일자(초)", example = "15552000(6개월)")
        val refreshTokenExpiresIn: Int,
    ) : SocialLoginResponse()

    data class Pending(
        @field:Schema(description = "소셜 로그인 성공 여부", example = "pending")
        val status: String = "pending",
        @field:Schema(description = "registerToken", example = "registerToken")
        val registerToken: String,
    ) : SocialLoginResponse()
}
