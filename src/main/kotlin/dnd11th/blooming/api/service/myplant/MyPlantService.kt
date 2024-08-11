package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantManageRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.MyPlant
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
    private val myPlantMessageFactory: MyPlantMessageFactory,
) {
    @Transactional
    fun saveMyPlant(
        request: MyPlantSaveRequest,
        now: LocalDate,
    ): MyPlantSaveResponse {
        validateDateNotInFuture(request.startDate, now)
        validateDateNotInFuture(request.lastWateredDate, now)
        validateDateNotInFuture(request.lastFertilizerDate, now)

        val myPlant = request.toMyPlant()

        val savedPlant = myPlantRepository.save(myPlant)

        return MyPlantSaveResponse.from(savedPlant)
    }

    @Transactional(readOnly = true)
    fun findAllMyPlant(
        now: LocalDate,
        locationId: Long? = null,
        sort: MyPlantQueryCreteria = MyPlantQueryCreteria.CreatedDesc,
    ): List<MyPlantResponse> {
        val plantList = findSortedMyPlants(locationId, sort)

        return plantList.stream().map { plant ->
            MyPlantResponse.of(plant, now)
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findMyPlantDetail(
        myPlantId: Long,
        now: LocalDate,
    ): MyPlantDetailResponse {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        return MyPlantDetailResponse.of(plant, myPlantMessageFactory, now)
    }

    @Transactional
    fun modifyMyPlant(
        myPlantId: Long,
        request: MyPlantModifyRequest,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        plant.modify(
            nickname = request.nickname,
            location =
                request.location?.let {
                    locationRepository.findByName(request.location)
                        ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)
                },
            startDate = request.startDate,
            lastWateredDate = request.lastWateredDate,
            lastFertilizerDate = request.lastFertilizerDate,
        )
    }

    @Transactional
    fun deleteMyPlant(myPlantId: Long) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlantRepository.delete(plant)
    }

    @Transactional
    fun modifyMyPlantAlarm(
        myPlantId: Long,
        request: AlarmModifyRequest,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        plant.alarm = request.toAlarm()
    }

    @Transactional
    fun manageMyPlant(
        myPlantId: Long,
        request: MyPlantManageRequest,
        now: LocalDate,
    ) {
        val plant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        plant.manageLastDates(request.doWater, request.doFertilizer, now)
    }

    private fun validateDateNotInFuture(
        targetDate: LocalDate,
        currentDate: LocalDate,
    ) {
        if (targetDate.isAfter(currentDate)) {
            throw InvalidDateException(ErrorType.INVALID_DATE)
        }
    }

    private fun findSortedMyPlants(
        locationId: Long?,
        sort: MyPlantQueryCreteria,
    ): List<MyPlant> {
        val location = locationId?.let { locationRepository.findByIdOrNull(locationId) }

        val plantList =
            when (sort) {
                MyPlantQueryCreteria.CreatedDesc -> myPlantRepository.findAllByLocationOrderByCreatedDateDesc(location)
                MyPlantQueryCreteria.CreatedAsc -> myPlantRepository.findAllByLocationOrderByCreatedDateAsc(location)
                MyPlantQueryCreteria.WateredDesc ->
                    myPlantRepository.findAllByLocationOrderByLastWateredDateDesc(
                        location,
                    )
                MyPlantQueryCreteria.WateredAsc ->
                    myPlantRepository.findAllByLocationOrderByLastWateredDateAsc(
                        location,
                    )
            }
        return plantList
    }
}
