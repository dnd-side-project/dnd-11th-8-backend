package dnd11th.blooming.api.service.location

import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.dto.location.LocationSaveResponse
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
        // TODO : 유저와 매핑 필요
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
                ?: throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)

        location.modifyName(request.name)

        return LocationResponse.from(location)
    }

    @Transactional
    fun deleteLocation(locationId: Long) {
        if (!locationRepository.existsById(locationId)) {
            throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)
        }

        // TODO : Location 삭제시 그 안에 있던 식물들을 어떻게 다룰지 추가 작성 필요

        locationRepository.deleteById(locationId)
    }
}
