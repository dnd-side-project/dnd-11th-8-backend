package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location

class LocationSaveResponse(
    val id: Long?,
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
