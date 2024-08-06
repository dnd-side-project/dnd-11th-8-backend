package dnd11th.blooming.api.controller.location

import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.api.service.location.LocationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/plants/location")
class LocationController(
    private val locationService: LocationService,
) {
    @PostMapping
    fun saveLocation(
        @RequestBody request: LocationSaveRequest,
    ): LocationSaveResponse = locationService.saveLocation(request)

    @GetMapping
    fun findAllLocation(): List<LocationResponse> = locationService.findAllLocation()

    @PatchMapping("/{locationId}")
    fun modifyLocation(
        @PathVariable locationId: Long,
        @RequestBody request: LocationModifyRequest,
    ): LocationResponse = locationService.modifyLocation(locationId, request)

    @DeleteMapping("/{locationId}")
    fun deleteLocation(
        @PathVariable locationId: Long,
    ) = locationService.deleteLocation(locationId)
}
