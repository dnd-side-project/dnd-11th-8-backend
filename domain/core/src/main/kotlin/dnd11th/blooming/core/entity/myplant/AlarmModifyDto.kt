package dnd11th.blooming.core.entity.myplant

data class AlarmModifyDto(
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
)
