package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class IdTokenRequest(
    @field:Schema(description = "idToken", example = "idToken")
    @field:NotBlank(message = "idToken은 필수값입니다.")
    val idToken: String,
)
