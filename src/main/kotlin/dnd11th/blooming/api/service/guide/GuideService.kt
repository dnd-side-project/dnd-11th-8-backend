package dnd11th.blooming.api.service.guide

import dnd11th.blooming.api.dto.guide.PlantGuideResponse
import dnd11th.blooming.api.dto.guide.PlantResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Month

@Service
class GuideService(
    private val plantRepository: PlantRepository,
    private val plantMessageFactory: PlantMessageFactory,
) {
    fun findPlantList(plantName: String): List<PlantResponse> {
        val plantList = plantRepository.findAllByNameContaining(plantName)

        return PlantResponse.fromList(plantList)
    }

    fun findPlantGuide(
        plantId: Long,
        month: Month,
    ): PlantGuideResponse {
        val plant =
            plantRepository.findByIdOrNull(plantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_PLANT)

        return PlantGuideResponse.of(plant, month, plantMessageFactory)
    }
}
