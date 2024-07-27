package dnd11th.blooming.api.dto

data class AlarmEditRequest(
    val waterAlarm: Boolean?,
    val waterPeriod: Int?,
    val nutrientsAlarm: Boolean?,
    val nutrientsPeriod: Int?,
    val repotAlarm: Boolean?,
    val repotPeriod: Int?,
)
