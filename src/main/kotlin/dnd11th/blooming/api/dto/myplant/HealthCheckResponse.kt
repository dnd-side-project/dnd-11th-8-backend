package dnd11th.blooming.api.dto.myplant

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "MyPlant HealthCheck Response",
    description = "내 식물 관심주기 응답",
)
data class HealthCheckResponse(
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val tipMessage: String,
)
