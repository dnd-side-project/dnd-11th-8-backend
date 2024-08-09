package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm

data class AlarmModifyRequest(
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
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
