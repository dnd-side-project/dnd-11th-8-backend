package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.HealthCheckResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.core.entity.location.Location
import dnd11th.blooming.core.entity.myplant.AlarmModifyDto
import dnd11th.blooming.core.entity.myplant.MyPlant
import dnd11th.blooming.core.entity.myplant.MyPlantCreateDto
import dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl
import dnd11th.blooming.core.entity.plant.Plant
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.core.repository.image.ImageRepository
import dnd11th.blooming.core.repository.location.LocationRepository
import dnd11th.blooming.core.repository.myplant.MyPlantRepository
import dnd11th.blooming.core.repository.plant.PlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.Month

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
        now: LocalDate,
    ): MyPlantSaveResponse {
        val location: Location? = dto.locationId?.let { locationRepository.findByIdAndUser(it, user) }
        val plant: Plant? = dto.plantId?.let { plantRepository.findByIdOrNull(it) }

        val myPlant = MyPlant.createMyPlant(dto, location, plant, user, now)

        val savedPlant = myPlantRepository.save(myPlant)

        return MyPlantSaveResponse.from(savedPlant, myPlantMessageFactory)
    }

    @Transactional(readOnly = true)
    fun findAllMyPlant(
        now: LocalDate,
        user: User,
    ): List<MyPlantResponse> {
        val myPlantsWithImageUrl: List<MyPlantWithImageUrl> = myPlantRepository.findMyPlantAndMostRecentFavoriteImageByUser(user)

        return MyPlantResponse.fromMyPlantWithImageUrlList(myPlantsWithImageUrl, now)
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
        now: LocalDate,
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
            now = now,
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
        dto: AlarmModifyDto,
        user: User,
        month: Month,
    ) {
        val myPlant =
            myPlantRepository.findByIdAndUser(myPlantId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_MYPLANT)

        myPlant.modifyAlarm(dto, month)
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
}
