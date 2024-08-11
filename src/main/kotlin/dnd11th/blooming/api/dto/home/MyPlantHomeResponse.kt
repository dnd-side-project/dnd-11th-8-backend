package dnd11th.blooming.api.dto.home

import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantHomeResponse(
    val myPlantId: Long,
    val nickname: String,
    val scientificName: String,
    val waterAlarm: Boolean,
    val waterRemainDay: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerRemainDay: Int?,
    val healthCheckAlarm: Boolean,
) {
    companion object {
        fun of(
            myPlant: MyPlant,
            now: LocalDate,
        ): MyPlantHomeResponse =
            MyPlantHomeResponse(
                myPlantId = myPlant.id,
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
                waterAlarm = myPlant.alarm.waterAlarm,
                waterRemainDay = myPlant.getWaterRemainDay(now),
                fertilizerAlarm = myPlant.alarm.fertilizerAlarm,
                fertilizerRemainDay = myPlant.getFerilizerRemainDate(now),
                healthCheckAlarm = myPlant.alarm.healthCheckAlarm,
            )
    }
}
