package dnd11th.blooming.service.implement

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PlantWriter(
    private val repository: PlantRepository,
) {
    fun saveOne(request: PlantSaveRequest): PlantSaveResponse {
        val plant =
            Plant(
                scientificName = request.scientificName,
                name = request.name,
                startDate = request.startDate,
                lastWateredDate = request.lastWateredDate,
                waterAlarm = request.waterAlarm,
                nutrientsAlarm = request.nutrientsAlarm,
            )

        return PlantSaveResponse.from(repository.save(plant))
    }
}
