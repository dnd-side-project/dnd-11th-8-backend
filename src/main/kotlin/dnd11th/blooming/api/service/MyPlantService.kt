package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.AlarmResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MyPlantService(
    private val myPlantRepository: MyPlantRepository,
    private val locationRepository: LocationRepository,
) {
    @Transactional
    fun savePlant(
        request: MyPlantSaveRequest,
        now: LocalDate,
    ) {
        validateDateNotInFuture(request.startDate, now)
        validateDateNotInFuture(request.lastWateredDate, now)
        validateDateNotInFuture(request.lastFertilizerDate, now)

        val myPlant = request.toMyPlant()

        myPlantRepository.save(myPlant)
    }

    private fun validateDateNotInFuture(
        targetDate: LocalDate,
        currentDate: LocalDate,
    ) {
        if (targetDate.isAfter(currentDate)) {
            throw InvalidDateException(ErrorType.INVALID_DATE)
        }
    }

    @Transactional(readOnly = true)
    fun findAllPlant(now: LocalDate): List<MyPlantResponse> {
        val plantList = myPlantRepository.findAll()

        return plantList.stream().map { plant ->
            MyPlantResponse.from(plant, now)
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findPlantDetail(myPlantId: Long): MyPlantDetailResponse {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        return MyPlantDetailResponse.from(plant)
    }

    @Transactional
    fun modifyMyPlant(
        myPlantId: Long,
        request: MyPlantModifyRequest,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        plant.modify(
            nickname = request.nickname,
            location =
                request.location?.let {
                    locationRepository.findByName(request.location)
                        ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION_ID)
                },
            startDate = request.startDate,
            lastWateredDate = request.lastWateredDate,
            lastFertilizerDate = request.lastFertilizerDate,
        )
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
    fun modifyPlantAlarm(
        myPlantId: Long,
        request: AlarmModifyRequest,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)

        plant.alarm = request.toAlarm()
    }
}
