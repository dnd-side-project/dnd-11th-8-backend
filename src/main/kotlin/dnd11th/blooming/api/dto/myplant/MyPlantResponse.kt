package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantResponse(
    val myPlantId: Long?,
    val nickname: String,
    val scientificName: String,
    val waterRemainDay: Int?,
    val fertilizerRemainDay: Int?,
) {
    companion object {
        fun of(
            myPlant: MyPlant,
            now: LocalDate,
        ): MyPlantResponse =
            MyPlantResponse(
                myPlantId = myPlant.id,
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
                waterRemainDay = myPlant.getWaterRemainDay(now),
                fertilizerRemainDay = myPlant.getFerilizerRemainDate(now),
            )
    }
}
