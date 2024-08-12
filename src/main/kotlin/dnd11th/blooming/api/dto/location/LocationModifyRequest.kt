package dnd11th.blooming.api.dto.location

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class LocationModifyRequest(
    @field:NotNull(message = "새로운 위치명은 필수값입니다.")
    @field:NotBlank(message = "새로운 위치명은 비어있을 수 없습니다.")
    val name: String,
)
