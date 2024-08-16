package dnd11th.blooming.api.service.guide

import dnd11th.blooming.api.dto.guide.PlantGuideResponse
import dnd11th.blooming.api.dto.guide.PlantResponse
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.stereotype.Service

@Service
class GuideService(
    private val plantRepository: PlantRepository,
) {
    fun findPlantList(plantName: String): List<PlantResponse> {
        val plantList = plantRepository.findAllByNameContaining(plantName)

        return PlantResponse.fromList(plantList)
    }

    fun findPlantGuide(guideId: Long): PlantGuideResponse {
        TODO()
    }
}
