package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Location

data class LocationModifyRequest(
    val name: String,
) {
    fun modifyLocation(location: Location): Location {
        location.name = name
        return location
    }
}
