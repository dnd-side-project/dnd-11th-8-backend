package dnd11th.blooming.api.controller.location

import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.api.service.location.LocationService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.User
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/location")
class LocationController(
    private val locationService: LocationService,
) : LocationApi {
    @Secured
    @PostMapping
    override fun saveLocation(
        @RequestBody @Valid request: LocationSaveRequest,
        @LoginUser user: User,
    ): LocationSaveResponse = locationService.saveLocation(request.toLocationCreateDto(), user)

    @Secured
    @GetMapping
    override fun findAllLocation(
        @LoginUser user: User,
    ): List<LocationResponse> = locationService.findAllLocation(user)

    @Secured
    @PatchMapping("/{locationId}")
    override fun modifyLocation(
        @PathVariable locationId: Long,
        @RequestBody @Valid request: LocationModifyRequest,
        @LoginUser user: User,
    ): LocationResponse = locationService.modifyLocation(locationId, request, user)

    @Secured
    @DeleteMapping("/{locationId}")
    override fun deleteLocation(
        @PathVariable locationId: Long,
        @LoginUser user: User,
    ) = locationService.deleteLocation(locationId, user)
}
