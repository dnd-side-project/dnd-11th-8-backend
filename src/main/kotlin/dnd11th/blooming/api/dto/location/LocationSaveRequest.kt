package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Location Save Request",
    description = "위치 저장 요청",
)
data class LocationSaveRequest(
    @field:Schema(description = "새로운 위치 이름", example = "부엌")
    val name: String,
) {
    fun toLocation(): Location =
        Location(
            name = name,
        )
}
