package dnd11th.blooming.api.dto

import java.time.LocalDate

data class PlantSaveRequest(
    val scientificName: String,
    val nickname: String,
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val waterAlarm: Boolean?,
    val waterPeriod: Int?,
    val nutrientsAlarm: Boolean?,
    val nutrientsPeriod: Int?,
    val repotAlarm: Boolean?,
    val repotPeriod: Int?,
)
