package dnd11th.blooming.api.dto.myplant

import java.time.LocalDate

data class MyPlantCreateDto(
    val nickname: String,
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val lastFertilizerDate: LocalDate,
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
)
