package dnd11th.blooming.api.service.location

import dnd11th.blooming.api.dto.location.LocationCreateDto
import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.api.dto.location.MyPlantExistInLocationResponse
import dnd11th.blooming.common.exception.BadRequestException
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationRepository: LocationRepository,
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveLocation(
        dto: LocationCreateDto,
        user: User,
    ): LocationSaveResponse {
        if (locationRepository.countByUser(user) >= 3) throw BadRequestException(ErrorType.LOCATION_COUNT_EXCEED)

        val location = Location.createLocation(dto, user)

        val savedLocation = locationRepository.save(location)

        return LocationSaveResponse.from(savedLocation)
    }

    @Transactional(readOnly = true)
    fun findAllLocation(user: User): List<LocationResponse> {
        val locations = locationRepository.findAllByUser(user)

        return LocationResponse.fromList(locations)
    }

    @Transactional
    fun modifyLocation(
        locationId: Long,
        request: LocationModifyRequest,
        user: User,
    ): LocationResponse {
        val location =
            locationRepository.findByIdAndUser(locationId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)

        location.modifyName(request.name!!)

        return LocationResponse.from(location)
    }

    @Transactional(readOnly = true)
    fun myPlantExistInLocation(
        locationId: Long,
        user: User,
    ): MyPlantExistInLocationResponse {
        val isExist = myPlantRepository.existsByLocationId(locationId)

        return MyPlantExistInLocationResponse(isExist)
    }

    @Transactional
    fun deleteLocation(
        locationId: Long,
        user: User,
    ) {
        val location =
            locationRepository.findByIdAndUser(locationId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)

        val myPlants = myPlantRepository.findAllByLocation(location)

        imageRepository.deleteAllByMyPlantIn(myPlants)
        myPlantRepository.deleteAllByLocation(location)
        locationRepository.delete(location)
    }
}
