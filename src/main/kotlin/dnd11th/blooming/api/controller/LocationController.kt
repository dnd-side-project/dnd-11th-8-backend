package dnd11th.blooming.api.controller

import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.api.service.LocationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(
    private val locationService: LocationService,
) {
    @PostMapping("/location")
    fun saveLocation(
        @RequestBody request: LocationSaveRequest,
    ): LocationSaveResponse = locationService.saveLocation(request)
}