package dnd11th.blooming.api.dto.guide

import dnd11th.blooming.domain.entity.plant.Plant
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Plant Search Response",
    description = "식물 검색 응답",
)
data class PlantResponse(
    @field:Schema(description = "식물 ID", example = "1")
    val plantId: Long,
    @field:Schema(description = "식물 이름", example = "몬스테라 델리오사")
    val name: String,
    @field:Schema(description = "식물 이미지 URL", example = "image.com")
    val imageUrl: String,
) {
    companion object {
        fun from(plant: Plant): PlantResponse =
            PlantResponse(
                plantId = plant.id ?: 0,
                name = plant.korName,
                imageUrl = plant.imageUrl,
            )

        fun fromList(plantList: List<Plant>): List<PlantResponse> = plantList.map { from(it) }.toList()
    }
}
