package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.LocationModifyRequest
import dnd11th.blooming.api.dto.LocationResponse
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.api.service.LocationService
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
class MyPlantLocationController(
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
