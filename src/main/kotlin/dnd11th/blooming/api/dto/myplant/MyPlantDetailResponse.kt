package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class MyPlantDetailResponse(
    val nickname: String,
    val scientificName: String,
    val startDate: LocalDate,
    val lastWatedDate: LocalDate,
    // TODO: 식물에 대한 상세 정보 추가 필요
) {
    companion object {
        fun from(myPlant: MyPlant): MyPlantDetailResponse =
            MyPlantDetailResponse(
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
                startDate = myPlant.startDate,
                lastWatedDate = myPlant.lastWateredDate,
            )
    }
}
