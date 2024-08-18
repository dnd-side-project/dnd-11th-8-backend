package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponse(
    @field:Schema(description = "AccessToken", example = "accessToken")
    val accessToken: String,
    @field:Schema(description = "accessToken 만료일자(초)", example = "604800(1주일)")
    val expiresIn: Int,
    @field:Schema(description = "RefreshToken", example = "refreshToken")
    val refreshToken: String,
    @field:Schema(description = "refreshToken 만료일자(초)", example = "15552000(6개월)")
    val refreshTokenExpiresIn: Int,
)
