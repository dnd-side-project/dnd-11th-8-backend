package dnd11th.blooming.api.dto

import java.time.LocalDate

data class PlantSaveRequest(
    val scientificName: String,
    val name: String,
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val waterAlarm: Boolean,
    val nutrientsAlarm: Boolean,
)
