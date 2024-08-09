package dnd11th.blooming.api.dto.myplant

import java.time.LocalDate

data class MyPlantModifyRequest(
    val nickname: String?,
    val location: String?,
    val startDate: LocalDate?,
    val lastWateredDate: LocalDate?,
    val lastFertilizerDate: LocalDate?,
)
