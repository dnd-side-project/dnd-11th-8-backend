package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(
    name = "Alarm Modify Request",
    description = "알림 수정 요청",
)
data class AlarmModifyRequest(
    @NotNull(message = "물주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean,
    @field:Schema(description = "물주기 알림 주기", example = "4")
    val waterPeriod: Int?,
    @NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean,
    @field:Schema(description = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    @field:Schema(description = "건강확인 알림 여부", example = "true")
    val healthCheckAlarm: Boolean,
) {
    fun toAlarm(): Alarm =
        Alarm(
            waterAlarm = waterAlarm,
            waterPeriod = waterPeriod,
            fertilizerAlarm = fertilizerAlarm,
            fertilizerPeriod = fertilizerPeriod,
            healthCheckAlarm = healthCheckAlarm,
        )
}
