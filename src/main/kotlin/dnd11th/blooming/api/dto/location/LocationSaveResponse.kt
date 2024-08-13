package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Location Save Response",
    description = "위치 저장 후 응답",
)
class LocationSaveResponse(
    @field:Schema(name = "저장된 위치 ID", example = "4")
    val id: Long,
    @field:Schema(name = "위치 이름", example = "부엌")
    val name: String,
) {
    companion object {
        fun from(location: Location): LocationSaveResponse =
            LocationSaveResponse(
                id = location.id,
                name = location.name,
            )
    }
}
