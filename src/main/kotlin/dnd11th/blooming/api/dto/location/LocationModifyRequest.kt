package dnd11th.blooming.api.dto.location

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Location Modify Request",
    description = "위치 수정 요청",
)
data class LocationModifyRequest(
	@field:Schema(description = "수정할 위치 이름", example = "거실")
    @NotNull(message = "새로운 위치명은 필수값입니다.")
    @NotBlank(message = "새로운 위치명은 비어있을 수 없습니다.")
    val name: String,
)
