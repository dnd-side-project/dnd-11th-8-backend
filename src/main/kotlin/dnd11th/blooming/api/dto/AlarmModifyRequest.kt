package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Alarm

data class AlarmModifyRequest(
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val nutrientsAlarm: Boolean,
    val nutrientsPeriod: Int?,
    val repotAlarm: Boolean,
    val repotPeriod: Int?,
) {
    fun toAlarm(): Alarm =
        Alarm(
            waterAlarm = waterAlarm,
            waterPeriod = waterPeriod,
            nutrientsAlarm = nutrientsAlarm,
            nutrientsPeriod = nutrientsPeriod,
            repotAlarm = repotAlarm,
            repotPeriod = repotPeriod,
        )
}
