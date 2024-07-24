package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Plant
import java.time.LocalDate

data class PlantDetailResponse(
    val name: String,
    val scientificName: String,
    val startDate: LocalDate,
    val lastWatedDate: LocalDate,
    // TODO: 식물에 대한 상세 정보 추가 필요
) {
    companion object {
        fun from(plant: Plant): PlantDetailResponse =
            PlantDetailResponse(
                name = plant.name,
                scientificName = plant.scientificName,
                startDate = plant.startDate,
                lastWatedDate = plant.lastWateredDate,
            )
    }
}
