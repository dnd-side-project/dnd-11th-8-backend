package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.DeviceTokenRequest
import dnd11th.blooming.api.service.user.DeviceTokenService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.domain.entity.user.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/device-token")
class DeviceTokenController(
    private val deviceTokenService: DeviceTokenService,
) {
    @PostMapping
    fun saveDeviceToken(
        @LoginUser user: User,
        @RequestBody deviceTokenRequest: DeviceTokenRequest,
    ): ResponseEntity<Void> {
        deviceTokenService.saveToken(user, deviceTokenRequest.deviceToken)
        return ResponseEntity.noContent().build()
    }
}
