package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlantService(
    private val plantRepository: PlantRepository,
) {
    fun savePlant(request: PlantSaveRequest): PlantSaveResponse {
        val plant =
            Plant(
                scientificName = request.scientificName,
                name = request.name,
                startDate = request.startDate,
                lastWateredDate = request.lastWateredDate,
                waterAlarm = request.waterAlarm,
                nutrientsAlarm = request.nutrientsAlarm,
            )
        return PlantSaveResponse.from(plantRepository.save(plant))
    }
}
