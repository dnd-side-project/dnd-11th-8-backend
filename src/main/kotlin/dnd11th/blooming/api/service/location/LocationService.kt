package dnd11th.blooming.api.service.location

import dnd11th.blooming.api.dto.location.LocationCreateDto
import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.common.exception.BadRequestException
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.core.entity.location.Location
import dnd11th.blooming.domain.core.entity.user.User
import dnd11th.blooming.domain.core.repository.location.LocationRepository
import dnd11th.blooming.domain.core.repository.myplant.MyPlantRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationRepository: LocationRepository,
    private val myPlantRepository: MyPlantRepository,
) {
    @Transactional
    fun saveLocation(
        dto: LocationCreateDto,
        user: User,
    ): LocationSaveResponse {
        val locations = locationRepository.findAllByUser(user)

        validateLocationSizeNotExceed(locations.size)
        validateLocationNameUnique(locations, dto.name)

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

    @Transactional
    fun deleteLocation(
        locationId: Long,
        user: User,
    ) {
        val location =
            locationRepository.findByIdAndUser(locationId, user)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)

        myPlantRepository.nullifyLocationByLocation(location)

        locationRepository.delete(location)
    }

    companion object {
        private const val MAX_LOCATION_LIMIT = 3
    }

    private fun validateLocationNameUnique(
        locations: List<Location>,
        newLocationName: String,
    ) {
        if (locations.any { location ->
                location.name == newLocationName
            }
        ) {
            throw BadRequestException(ErrorType.LOCATION_NAME_DUPLICATE)
        }
    }

    private fun validateLocationSizeNotExceed(locationsSize: Int) {
        if (locationsSize >= MAX_LOCATION_LIMIT) throw BadRequestException(ErrorType.LOCATION_COUNT_EXCEED)
    }
}
