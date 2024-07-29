package dnd11th.blooming.api.dto

import dnd11th.blooming.domain.entity.MyPlant

class MyPlantSaveResponse(
    val myPlantId: Long,
) {
    companion object {
        fun from(myPlant: MyPlant): MyPlantSaveResponse =
            MyPlantSaveResponse(
                myPlantId = myPlant.id,
            )
    }
}
