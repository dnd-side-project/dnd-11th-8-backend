package dnd11th.blooming.api.dto.location

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Location Modify Request",
    description = "위치 수정 요청",
)
data class LocationModifyRequest(
    @field:Schema(name = "수정할 위치 이름", example = "거실")
    val name: String,
)
