package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.MyPlant

data class MyPlantResponse(
    val id: Long,
    val nickname: String,
    val scientificName: String,
) {
    companion object {
        fun from(myPlant: MyPlant): MyPlantResponse =
            MyPlantResponse(
                id = myPlant.id,
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
            )
    }
}
