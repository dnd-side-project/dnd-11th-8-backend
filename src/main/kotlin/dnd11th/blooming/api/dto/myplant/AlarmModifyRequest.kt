package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import jakarta.validation.constraints.NotNull

data class AlarmModifyRequest(
    @NotNull(message = "물주기 알림 여부는 필수값입니다.")
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    @NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    @NotNull(message = "건강확인 알림 여부는 필수값입니다.")
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
