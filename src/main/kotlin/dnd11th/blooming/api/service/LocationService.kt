package dnd11th.blooming.api.service

import dnd11th.blooming.api.dto.LocationResponse
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.domain.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository,
) {
    fun saveLocation(request: LocationSaveRequest): LocationSaveResponse {
        val location = request.toLocation()
        return LocationSaveResponse.from(locationRepository.save(location))
    }

    fun findAllLocation(): List<LocationResponse> {
        val locations = locationRepository.findAll()
        return LocationResponse.fromList(locations)
    }
}
