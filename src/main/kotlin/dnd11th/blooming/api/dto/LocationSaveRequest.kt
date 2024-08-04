package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Location

data class LocationSaveRequest(
    val name: String,
) {
    fun toLocation(): Location =
        Location(
            name = name,
        )
}
