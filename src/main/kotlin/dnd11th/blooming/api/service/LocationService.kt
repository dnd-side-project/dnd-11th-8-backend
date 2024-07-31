package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.LocationModifyRequest
import dnd11th.blooming.api.dto.LocationResponse
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.repository.LocationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationRepository: LocationRepository,
) {
    @Transactional
    fun saveLocation(request: LocationSaveRequest): LocationSaveResponse {
        val location = request.toLocation()
        return LocationSaveResponse.from(locationRepository.save(location))
    }

    @Transactional(readOnly = true)
    fun findAllLocation(): List<LocationResponse> {
        val locations = locationRepository.findAll()
        return LocationResponse.fromList(locations)
    }

    @Transactional
    fun modifyLocation(
        locationId: Long,
        request: LocationModifyRequest,
    ): LocationResponse {
        val location =
            locationRepository.findByIdOrNull(locationId)
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION_ID)

        location.modifyName(request.name)

        return LocationResponse.from(location)
    }

    @Transactional
    fun deleteLocation(locationId: Long) {
        if (!locationRepository.existsById(locationId)) {
            throw NotFoundException(ErrorType.NOT_FOUND_LOCATION_ID)
        }

        locationRepository.deleteById(locationId)
    }
}
