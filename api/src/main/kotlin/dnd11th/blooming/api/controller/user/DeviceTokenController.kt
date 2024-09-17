package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.annotation.LoginUser
import dnd11th.blooming.api.annotation.Secured
import dnd11th.blooming.api.dto.user.DeviceTokenRequest
import dnd11th.blooming.api.service.user.DeviceTokenService
import dnd11th.blooming.core.entity.user.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/device-token")
class DeviceTokenController(
    private val deviceTokenService: DeviceTokenService,
) : DeviceTokenApi {
    @Secured
    @PostMapping
    override fun saveDeviceToken(
        @LoginUser user: User,
        @RequestBody deviceTokenRequest: DeviceTokenRequest,
    ): ResponseEntity<Void> {
        deviceTokenService.saveToken(user, deviceTokenRequest.deviceToken)
        return ResponseEntity.noContent().build()
    }

    @Secured
    @PostMapping("/invalid")
    override fun invalidDeviceToken(
        @LoginUser user: User,
    ): ResponseEntity<Void> {
        deviceTokenService.invalidToken(user)
        return ResponseEntity.noContent().build()
    }
}
