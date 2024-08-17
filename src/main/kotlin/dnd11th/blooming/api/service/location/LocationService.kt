package dnd11th.blooming.api.service.location

import dnd11th.blooming.api.dto.location.LocationCreateDto
import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.api.dto.location.MyPlantExistInLocationResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationService(
    private val locationRepository: LocationRepository,
    private val myPlantRepository: MyPlantRepository,
    private val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveLocation(dto: LocationCreateDto): LocationSaveResponse {
        // TODO : 유저와 매핑 필요

        val location = Location.createLocation(dto)

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

        location.modifyName(request.name!!)

        return LocationResponse.from(location)
    }

    @Transactional(readOnly = true)
    fun myPlantExistInLocation(locationId: Long): MyPlantExistInLocationResponse {
        val isExist = myPlantRepository.existsByLocationId(locationId)
        return MyPlantExistInLocationResponse(isExist)
    }

    @Transactional
    fun deleteLocation(locationId: Long) {
        if (!locationRepository.existsById(locationId)) {
            throw NotFoundException(ErrorType.NOT_FOUND_LOCATION)
        }

        val myPlants = myPlantRepository.findAllByLocationId(locationId)

        imageRepository.deleteAllByMyPlantIn(myPlants)
        myPlantRepository.deleteAllByLocationId(locationId)
        locationRepository.deleteById(locationId)
    }
}
