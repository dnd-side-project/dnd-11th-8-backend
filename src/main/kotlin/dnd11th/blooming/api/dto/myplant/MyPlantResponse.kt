package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant

data class MyPlantResponse(
    val myPlantId: Long,
    val nickname: String,
    val scientificName: String,
) {
    companion object {
        fun from(myPlant: MyPlant): MyPlantResponse =
            MyPlantResponse(
                myPlantId = myPlant.id,
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
            )
    }
}
