package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Device Token Request",
    description = "Device Token 등록 및 삭제",
)
data class DeviceTokenRequest(
    @field:Schema(description = "deviceToken", example = "deviceToken")
    val deviceToken: String,
)
