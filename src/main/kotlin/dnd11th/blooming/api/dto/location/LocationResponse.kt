package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Location Response",
    description = "위치 응답",
)
data class LocationResponse(
    @field:Schema(name = "위치 ID", example = "3")
    val id: Long,
    @field:Schema(name = "위치 이름", example = "베란다")
    val name: String,
) {
    companion object {
        fun fromList(locations: List<Location>): List<LocationResponse> =
            locations.stream()
                .map { location ->
                    LocationResponse(
                        id = location.id,
                        name = location.name,
                    )
                }
                .toList()

        fun from(location: Location): LocationResponse =
            LocationResponse(
                id = location.id,
                name = location.name,
            )
    }
}
