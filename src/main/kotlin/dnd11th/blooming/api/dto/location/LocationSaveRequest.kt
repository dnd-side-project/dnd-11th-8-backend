package dnd11th.blooming.api.dto.location

import dnd11th.blooming.domain.entity.Location
import jakarta.validation.constraints.NotBlank

data class LocationSaveRequest(
    @field:NotBlank(message = "새로운 위치명은 필수값입니다.")
    val name: String?,
) {
    fun toLocation(): Location =
        Location(
            name = name!!,
        )
}
