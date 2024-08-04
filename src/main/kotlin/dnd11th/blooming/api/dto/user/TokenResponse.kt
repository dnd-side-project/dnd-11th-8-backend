package dnd11th.blooming.api.dto.user

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
