package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location

data class LocationResponse(
    val id: Long,
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
