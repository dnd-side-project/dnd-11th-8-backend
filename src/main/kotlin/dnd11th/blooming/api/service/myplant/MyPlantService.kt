package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.HealthCheckResponse
import dnd11th.blooming.api.dto.myplant.MyPlantCreateDto
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.PlantRepository
import dnd11th.blooming.domain.repository.myplant.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MyPlantService(
    private val myPlantRepository: MyPlantRepository,
    private val plantRepository: PlantRepository,
    private val locationRepository: LocationRepository,
    private val myPlantMessageFactory: MyPlantMessageFactory,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveMyPlant(
        dto: MyPlantCreateDto,
        user: User,
    ): MyPlantSaveResponse {
        val location: Location? = dto.locationId?.let { locationRepository.findByIdAndUser(it, user) }
        val plant: Plant? = dto.plantId?.let { plantRepository.findByIdOrNull(it) }

        val myPlant = MyPlant.createMyPlant(dto, location, plant, user)

        val savedPlant = myPlantRepository.save(myPlant)

        return MyPlantSaveResponse.from(savedPlant)
    }

    @Transactional(readOnly = true)
    fun findAllMyPlant(
        now: LocalDate,
        locationId: Long? = null,
        sort: MyPlantQueryCreteria = MyPlantQueryCreteria.CreatedDesc,
        user: User,
    ): List<MyPlantResponse> {
        val myPlantWithUrlList = findSortedMyPlantsWithImage(locationId, user, sort)

        // TODO : 디폴트 이미지 url 넣어야 함
        return myPlantWithUrlList.stream().map { myPlantAndImageUrl ->
            MyPlantResponse.of(
                myPlantAndImageUrl.first,
                myPlantAndImageUrl.second,
                "url",
                now,
            )
        }.toList()
    }

    @Transactional(readOnly = true)
    fun findMyPlantDetail(
        myPlantId: Long,
        now: LocalDate,
        user: User,
    ): MyPlantDetailResponse {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val myPlantImages = imageRepository.findAllByMyPlant(myPlant)

        return MyPlantDetailResponse.of(myPlant, myPlantMessageFactory, myPlantImages, now)
    }

    @Transactional
    fun modifyMyPlant(
        myPlantId: Long,
        request: MyPlantModifyRequest,
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        val location =
            request.locationId?.let {
                locationRepository.findByIdAndUser(request.locationId, user)
                    ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)
            }

        myPlant.modify(
            nickname = request.nickname,
            location = location,
            startDate = request.startDate,
            lastWateredDate = request.lastWateredDate,
            lastFertilizerDate = request.lastFertilizerDate,
        )
    }

    @Transactional
    fun deleteMyPlant(
        myPlantId: Long,
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        imageRepository.deleteAllInBatchByMyPlant(myPlant)
        myPlantRepository.delete(myPlant)
    }

    @Transactional
    fun modifyMyPlantAlarm(
        myPlantId: Long,
        request: AlarmModifyRequest,
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.modifyAlarm(request.toAlarm())
    }

    @Transactional
    fun waterMyPlant(
        myPlantId: Long,
        now: LocalDate,
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.doWater(now)
    }

    @Transactional
    fun fertilizerMyPlant(
        myPlantId: Long,
        now: LocalDate,
        user: User,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.doFertilizer(now)
    }

    @Transactional
    fun healthCheckMyPlant(
        myPlantId: Long,
        now: LocalDate,
        user: User,
    ): HealthCheckResponse {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.doHealthCheck(now)

        return HealthCheckResponse(myPlantMessageFactory.createHealthCheckMessage())
    }

    private fun findSortedMyPlantsWithImage(
        locationId: Long?,
        user: User,
        sort: MyPlantQueryCreteria,
    ): List<Pair<MyPlant, String?>> {
        val location = locationId?.let { locationRepository.findByIdAndUser(locationId, user) }

        val sortedMyPlantList = myPlantRepository.findAllByLocationAndUserOrderBy(location, user, sort)

        val urlMap =
            imageRepository.findFavoriteImagesForMyPlants(sortedMyPlantList)
                .associate { it.myPlantId to it.imageUrl }

        return sortedMyPlantList.map { myPlant ->
            myPlant to urlMap[myPlant.id]
        }
    }
}
