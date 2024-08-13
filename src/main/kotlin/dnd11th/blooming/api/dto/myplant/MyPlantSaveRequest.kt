package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantSaveRequest(
    val scientificName: String,
    val nickname: String,
    val locationId: Long,
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val lastFertilizerDate: LocalDate,
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
) {
    fun toMyPlant(
        location: Location,
        now: LocalDate,
    ): MyPlant =
        MyPlant(
            scientificName = scientificName,
            nickname = nickname,
            startDate = startDate,
            lastWateredDate = lastWateredDate,
            lastFertilizerDate = lastFertilizerDate,
            currentDate = now,
            alarm =
                Alarm(
                    waterAlarm = waterAlarm,
                    waterPeriod = waterPeriod,
                    fertilizerAlarm = fertilizerAlarm,
                    fertilizerPeriod = fertilizerPeriod,
                    healthCheckAlarm = healthCheckAlarm,
                ),
        ).also {
            it.setLocationRelation(location)
            // TODO : 유저와 매핑 필요
            // TODO : 식물가이드와 매핑 필요
        }
}
