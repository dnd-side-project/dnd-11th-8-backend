package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.PlantDetailResponse
import dnd11th.blooming.api.dto.PlantResponse
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.AlarmRepository
import dnd11th.blooming.domain.repository.PlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class PlantService(
    private val plantRepository: PlantRepository,
    private val alarmRepository: AlarmRepository,
) {
    @Transactional
    fun savePlant(request: PlantSaveRequest): PlantSaveResponse {
        if (request.lastWateredDate.isAfter(LocalDate.now()) || request.startDate.isAfter(LocalDate.now())) {
            throw InvalidDateException(ErrorType.INVALID_DATE)
        }

        val alarm = Alarm.from(request)
        val plant = Plant.from(request, alarm)

        alarmRepository.save(alarm)
        return PlantSaveResponse.from(plantRepository.save(plant))
    }

    @Transactional(readOnly = true)
    fun findAllPlant(): List<PlantResponse> {
        val plantList = plantRepository.findAll()

        return plantList.stream().map { plant ->
            PlantResponse.from(plant)
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findPlantDetail(id: Long): PlantDetailResponse {
        val plant =
            plantRepository.findByIdOrNull(id)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_PLANT_ID)

        return PlantDetailResponse.from(plant)
    }
}
