package dnd11th.blooming.core.entity.myplant

import java.time.LocalDate

data class MyPlantCreateDto(
    val scientificName: String,
    val plantId: Long?,
    val nickname: String?,
    val locationId: Long?,
    val startDate: LocalDate,
    val lastWateredDate: Int,
    val lastFertilizerDate: Int,
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
)
