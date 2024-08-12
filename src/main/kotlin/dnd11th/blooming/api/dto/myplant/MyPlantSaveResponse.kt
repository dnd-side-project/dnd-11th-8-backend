package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant

data class MyPlantSaveResponse(
    val myPlantId: Long?,
) {
    val message: String = "등록 되었습니다."

    companion object {
        fun from(myPlant: MyPlant): MyPlantSaveResponse =
            MyPlantSaveResponse(
                myPlantId = myPlant.id,
            )
    }
}
