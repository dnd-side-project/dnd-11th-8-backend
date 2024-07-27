package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.AlarmEditRequest
import dnd11th.blooming.api.dto.AlarmResponse
import dnd11th.blooming.api.dto.MyPlantDetailResponse
import dnd11th.blooming.api.dto.MyPlantResponse
import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.dto.MyPlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MyPlantService(
    private val myPlantRepository: MyPlantRepository,
) {
    @Transactional
    fun savePlant(request: MyPlantSaveRequest): MyPlantSaveResponse {
        if (request.lastWateredDate.isAfter(LocalDate.now()) || request.startDate.isAfter(LocalDate.now())) {
            throw InvalidDateException(ErrorType.INVALID_DATE)
        }

        val myPlant =
            MyPlant(
                scientificName = request.scientificName,
                nickname = request.nickname,
                startDate = request.startDate,
                lastWateredDate = request.lastWateredDate,
                alarm =
                    Alarm(
                        waterAlarm = request.waterAlarm ?: false,
                        waterPeriod = request.waterPeriod ?: 3,
                        nutrientsAlarm = request.nutrientsAlarm ?: false,
                        nutrientsPeriod = request.nutrientsPeriod ?: 30,
                        repotAlarm = request.repotAlarm ?: false,
                        repotPeriod = request.repotPeriod ?: 60,
                    ),
            )

        return MyPlantSaveResponse.from(myPlantRepository.save(myPlant))
    }

    @Transactional(readOnly = true)
    fun findAllPlant(): List<MyPlantResponse> {
        val plantList = myPlantRepository.findAll()

        return plantList.stream().map { plant ->
            MyPlantResponse.from(plant)
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findPlantDetail(myPlantId: Long): MyPlantDetailResponse {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        return MyPlantDetailResponse.from(plant)
    }

    @Transactional(readOnly = true)
    fun findPlantAlarm(myPlantId: Long): AlarmResponse {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        val alarm = plant.alarm

        return AlarmResponse.from(alarm)
    }

    @Transactional
    fun editPlantAlarm(
        myPlantId: Long,
        request: AlarmEditRequest,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        val alarm = plant.alarm

        request.waterAlarm?.let { alarm.waterAlarm = it }
        request.waterPeriod?.let { alarm.waterPeriod = it }
        request.nutrientsAlarm?.let { alarm.nutrientsAlarm = it }
        request.nutrientsPeriod?.let { alarm.nutrientsPeriod = it }
        request.repotAlarm?.let { alarm.repotAlarm = it }
        request.repotPeriod?.let { alarm.repotPeriod = it }
    }
}
