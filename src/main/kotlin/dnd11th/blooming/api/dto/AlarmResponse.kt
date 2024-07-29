package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Alarm

data class AlarmResponse(
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val nutrientsAlarm: Boolean,
    val nutrientsPeriod: Int?,
    val repotAlarm: Boolean,
    val repotPeriod: Int?,
) {
    companion object {
        fun from(alarm: Alarm): AlarmResponse =
            AlarmResponse(
                waterAlarm = alarm.waterAlarm,
                waterPeriod = alarm.waterPeriod,
                nutrientsAlarm = alarm.nutrientsAlarm,
                nutrientsPeriod = alarm.nutrientsPeriod,
                repotAlarm = alarm.repotAlarm,
                repotPeriod = alarm.repotPeriod,
            )
    }
}
