package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.api.service.myplant.MyPlantMessageFactory
import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantDetailResponse(
    val nickname: String,
    val scientificName: String,
    val plantId: Int = 0,
    val location: String?,
    val startDate: LocalDate,
    val lastWateredTitle: String,
    val lastWateredInfo: String,
    val lastFertilizerTitle: String,
    val lastFertilizerInfo: String,
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    val healthCheckAlarm: Boolean,
) {
    companion object {
        fun of(
            myPlant: MyPlant,
            messageFactory: MyPlantMessageFactory,
            now: LocalDate,
        ): MyPlantDetailResponse =
            MyPlantDetailResponse(
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
                // TODO: 식물가이드 연관관계 매핑 후 plantId 세팅 필요
                location = myPlant.getLocationName(),
                startDate = myPlant.startDate,
                lastWateredTitle = messageFactory.createWateredTitle(),
                lastWateredInfo =
                    messageFactory.createWateredInfo(
                        myPlant.lastWateredDate,
                        now,
                    ),
                lastFertilizerTitle = messageFactory.createFertilizerTitle(),
                lastFertilizerInfo =
                    messageFactory.createFertilizerInfo(
                        myPlant.lastFertilizerDate,
                        now,
                    ),
                waterAlarm = myPlant.alarm.waterAlarm,
                waterPeriod = myPlant.alarm.waterPeriod,
                fertilizerAlarm = myPlant.alarm.fertilizerAlarm,
                fertilizerPeriod = myPlant.alarm.fertilizerPeriod,
                healthCheckAlarm = myPlant.alarm.healthCheckAlarm,
            )
    }
}
