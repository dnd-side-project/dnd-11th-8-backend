package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.DeviceTokenRequest
import dnd11th.blooming.core.entity.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "2. [유저]")
interface DeviceTokenApi {
    @Operation(summary = "Device Token 등록 API")
    @ApiResponse(responseCode = "204", description = "Device Token 등록 성공")
    fun saveDeviceToken(
        @Schema(hidden = true) user: User,
        deviceTokenRequest: DeviceTokenRequest,
    ): ResponseEntity<Void>

    @Operation(summary = "Device Token 삭제 API")
    @ApiResponse(responseCode = "204", description = "Device Token 삭제 성공")
    fun invalidDeviceToken(
        @Schema(hidden = true) user: User,
    ): ResponseEntity<Void>
}
