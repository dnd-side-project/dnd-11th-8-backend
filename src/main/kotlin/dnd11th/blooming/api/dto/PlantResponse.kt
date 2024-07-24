package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Plant

data class PlantResponse(
    val id: Long,
    val name: String,
    val scientificName: String,
) {
    companion object {
        fun from(plant: Plant): PlantResponse =
            PlantResponse(
                id = plant.id,
                name = plant.name,
                scientificName = plant.scientificName,
            )
    }
}
