package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.Plant

class PlantSaveResponse(
    val id: Long,
) {
    companion object {
        fun from(plant: Plant): PlantSaveResponse =
            PlantSaveResponse(
                id = plant.id,
            )
    }
}
