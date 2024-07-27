package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantSaveRequest(
    val scientificName: String,
    val nickname: String,
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val nutrientsAlarm: Boolean,
    val nutrientsPeriod: Int?,
    val repotAlarm: Boolean,
    val repotPeriod: Int?,
) {
    fun toMyPlant(): MyPlant =
        MyPlant(
            scientificName = scientificName,
            nickname = nickname,
            startDate = startDate,
            lastWateredDate = lastWateredDate,
            alarm =
                Alarm(
                    waterAlarm = waterAlarm,
                    waterPeriod = waterPeriod,
                    nutrientsAlarm = nutrientsAlarm,
                    nutrientsPeriod = nutrientsPeriod,
                    repotAlarm = repotAlarm,
                    repotPeriod = repotPeriod,
                ),
        )
}
