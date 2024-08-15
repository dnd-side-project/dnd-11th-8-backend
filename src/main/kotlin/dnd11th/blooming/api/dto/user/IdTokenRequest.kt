package dnd11th.blooming.api.dto.user

import jakarta.validation.constraints.NotBlank

data class IdTokenRequest(
    @field:NotBlank(message = "idToken은 필수값입니다.")
    val idToken: String,
)
