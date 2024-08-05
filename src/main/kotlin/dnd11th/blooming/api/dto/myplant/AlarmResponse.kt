package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm

data class AlarmResponse(
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
) {
    companion object {
        fun from(alarm: Alarm): AlarmResponse =
            AlarmResponse(
                waterAlarm = alarm.waterAlarm,
                waterPeriod = alarm.waterPeriod,
                fertilizerAlarm = alarm.fertilizerAlarm,
                fertilizerPeriod = alarm.fertilizerPeriod,
                healthCheckAlarm = alarm.healthCheckAlarm,
            )
    }
}
