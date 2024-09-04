package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "RefreshToken Request",
    description = "토큰 갱신 요청, 로그아웃 요청",
)
data class TokenRequest(
    @field:Schema(description = "RefreshToken", example = "refreshToken")
    val refreshToken: String,
)
