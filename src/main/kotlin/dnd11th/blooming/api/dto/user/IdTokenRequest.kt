package dnd11th.blooming.api.dto.user

import jakarta.validation.constraints.NotNull

data class IdTokenRequest(
    @NotNull(message = "idToken은 필수값입니다.")
    val idToken: String,
)
