package dnd11th.blooming.api.dto.location

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(
    name = "Location Modify Request",
    description = "위치 수정 요청",
)
data class LocationModifyRequest(
    @field:Schema(description = "수정할 위치 이름", example = "거실")
    @field:NotBlank(message = "새로운 위치명은 필수값입니다.")
    val name: String?,
)
