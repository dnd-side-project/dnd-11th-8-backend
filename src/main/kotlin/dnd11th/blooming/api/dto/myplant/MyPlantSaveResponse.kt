package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "MyPlant Save Response",
    description = "내 식물 저장 후 응답",
)
data class MyPlantSaveResponse(
    @field:Schema(description = "내 식물 ID", example = "17")
    val myPlantId: Long?,
) {
    @field:Schema(description = "저장메시지", example = "등록 되었습니다.")
    val message: String = "등록 되었습니다."

    companion object {
        fun from(myPlant: MyPlant): MyPlantSaveResponse =
            MyPlantSaveResponse(
                myPlantId = myPlant.id,
            )
    }
}
