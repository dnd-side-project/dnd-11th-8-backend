package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.core.entity.myplant.AlarmModifyDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(
    name = "Alarm Modify Request",
    description = "알림 수정 요청",
)
data class AlarmModifyRequest(
    @field:Schema(description = "물주기 알림 여부", example = "true")
    @field:NotNull(message = "물주기 알림 여부는 필수값입니다.")
    val waterAlarm: Boolean?,
    @field:Schema(description = "물주기 알림 주기", example = "4")
    val waterPeriod: Int?,
    @field:Schema(description = "비료주기 알림 여부", example = "false")
    @field:NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    val fertilizerAlarm: Boolean?,
    @field:Schema(description = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @field:Schema(description = "건강확인 알림 여부", example = "true")
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    val healthCheckAlarm: Boolean?,
) {
    fun toAlarmModifyDto(): AlarmModifyDto =
        AlarmModifyDto(
            waterAlarm = waterAlarm!!,
            waterPeriod = waterPeriod,
            fertilizerAlarm = fertilizerAlarm!!,
            fertilizerPeriod = fertilizerPeriod,
            healthCheckAlarm = healthCheckAlarm!!,
        )
}
