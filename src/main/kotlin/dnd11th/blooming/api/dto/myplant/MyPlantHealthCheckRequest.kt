package dnd11th.blooming.api.dto.myplant

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class MyPlantHealthCheckRequest(
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    @JsonProperty("healthCheck")
    private val _healthCheck: Boolean?,
) {
    val healthCheck: Boolean
        get() = _healthCheck!!
}
