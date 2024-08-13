package dnd11th.blooming.api.dto.myplant

import jakarta.validation.constraints.NotNull

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "HealthCheck Alarm Modify Request",
    description = "건강확인 알림 변경 요청",
)
data class MyPlantHealthCheckRequest(
    @field:Schema(description = "건강확인 알림 여부", example = "true")
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    val healthCheck: Boolean?,
)
