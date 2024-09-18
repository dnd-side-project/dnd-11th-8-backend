package dnd11th.blooming.core.entity.myplant

import java.time.LocalDate

data class UserPlantDto(
    val userId: Long,
    val userEmail: String,
    val myPlantId: Long,
    val plantNickname: String,
    val lastWateredDate: LocalDate,
    val waterPeriod: Int,
)
