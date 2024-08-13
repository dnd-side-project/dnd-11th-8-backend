package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "MyPlant Response",
    description = "내 식물 응답",
)
data class MyPlantResponse(
    @field:Schema(description = "내 식물 ID", example = "17")
    val myPlantId: Long,
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(description = "내 식물 학명", example = "몬스테라 델리오사")
    val scientificName: String,
    @field:Schema(description = "다음 물주기까지 남은 날짜", example = "4")
    val waterRemainDay: Int?,
    @field:Schema(description = "다음 비료주기까지 남은 날짜", example = "45")
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
