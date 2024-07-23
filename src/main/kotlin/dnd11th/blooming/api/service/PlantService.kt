package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.PlantDetailResponse
import dnd11th.blooming.api.dto.PlantResponse
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.data.repository.findByIdOrNull
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

    fun findAllPlant(): List<PlantResponse> {
        val plantList = plantRepository.findAll()

        return plantList.stream().map { plant ->
            PlantResponse.from(plant)
        }.toList()
    }

    fun findPlantDetail(id: Long): PlantDetailResponse {
        val plant =
            plantRepository.findByIdOrNull(id)
                ?: throw IllegalArgumentException("존재하지 않는 식물입니다.")
        // TODO: 커스텀 예외 추가 필요

        return PlantDetailResponse.from(plant)
    }
}
