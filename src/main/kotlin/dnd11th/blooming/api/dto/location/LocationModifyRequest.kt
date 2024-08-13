package dnd11th.blooming.api.dto.location

import jakarta.validation.constraints.NotBlank

data class LocationModifyRequest(
    @field:NotBlank(message = "새로운 위치명은 필수값입니다.")
    val name: String?,
)
