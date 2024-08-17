package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponse(
    @field:Schema(description = "AccessToken", example = "accessToken")
    val accessToken: String,
    @field:Schema(description = "RefreshToken", example = "refreshToken")
    val refreshToken: String,
)
