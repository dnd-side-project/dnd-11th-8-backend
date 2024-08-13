package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantHealthCheckRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.ImageRepository
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
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveMyPlant(
        request: MyPlantSaveRequest,
        now: LocalDate,
    ): MyPlantSaveResponse {
        val location =
            locationRepository
                .findByIdOrNull(request.locationId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)

        // TODO : 식물 가이드 데이터 가져오기 필요
        val scientificName = "몬스테라 델리오사"

        val myPlant = request.toMyPlant(location, now, scientificName)

        val savedPlant = myPlantRepository.save(myPlant)

        return MyPlantSaveResponse.from(savedPlant)
    }

    @Transactional(readOnly = true)
    fun findAllMyPlant(
        now: LocalDate,
        locationId: Long? = null,
        sort: MyPlantQueryCreteria = MyPlantQueryCreteria.CreatedDesc,
    ): List<MyPlantResponse> {
        val myPlantWithUrlList = findSortedMyPlantsWithImage(locationId, sort)

        return myPlantWithUrlList.stream().map { myPlantAndImageUrl ->
            MyPlantResponse.of(
                myPlantAndImageUrl.first,
                myPlantAndImageUrl.second,
                now,
            )
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findMyPlantDetail(
        myPlantId: Long,
        now: LocalDate,
    ): MyPlantDetailResponse {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val myPlantImages = imageRepository.findAllByMyPlant(myPlant)

        return MyPlantDetailResponse.of(myPlant, myPlantMessageFactory, myPlantImages, now)
    }

    @Transactional
    fun modifyMyPlant(
        myPlantId: Long,
        request: MyPlantModifyRequest,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.modify(
            nickname = request.nickname,
            location =
                request.locationId?.let {
                    locationRepository.findByIdOrNull(request.locationId)
                        ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)
                },
            startDate = request.startDate,
            lastWateredDate = request.lastWateredDate,
            lastFertilizerDate = request.lastFertilizerDate,
        )
    }

    @Transactional
    fun deleteMyPlant(myPlantId: Long) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        imageRepository.deleteAllInBatchByMyPlant(myPlant)
        myPlantRepository.delete(myPlant)
    }

    @Transactional
    fun modifyMyPlantAlarm(
        myPlantId: Long,
        request: AlarmModifyRequest,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.modifyAlarm(request.toAlarm())
    }

    @Transactional
    fun waterMyPlant(
        myPlantId: Long,
        now: LocalDate,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.doWater(now)
    }

    @Transactional
    fun fertilizerMyPlant(
        myPlantId: Long,
        now: LocalDate,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.doFertilizer(now)
    }

    @Transactional
    fun modifyMyPlantHealthCheck(
        myPlantId: Long,
        request: MyPlantHealthCheckRequest,
    ) {
        val myPlant =
            myPlantRepository.findByIdOrNull(myPlantId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.modifyHealthCheck(request.healthCheck!!)
    }

    private fun findSortedMyPlantsWithImage(
        locationId: Long?,
        sort: MyPlantQueryCreteria,
    ): List<Pair<MyPlant, String>> {
        val location = locationId?.let { locationRepository.findByIdOrNull(locationId) }

        val sortedMyPlantList =
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

        val urlMap =
            imageRepository.findFavoriteImagesForMyPlants(sortedMyPlantList)
                .associate { it.myPlantId to it.imageUrl }

        return sortedMyPlantList.map { myPlant ->
            myPlant to
                (
                    urlMap[myPlant.id]
                        ?: throw NotFoundException(ErrorType.NOT_FOUND_IMAGE)
                )
        }
    }
}
