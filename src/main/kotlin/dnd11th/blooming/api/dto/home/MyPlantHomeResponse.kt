package dnd11th.blooming.api.dto.home

import dnd11th.blooming.domain.core.entity.myplant.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "MyPlant Home Response",
    description = "홈화면에서의 내 식물 정보 응답",
)
data class MyPlantHomeResponse(
    @field:Schema(description = "내 식물 ID", example = "1")
    val myPlantId: Long?,
    @field:Schema(description = "내 식물 별명", example = "뿡뿡이")
    val nickname: String,
    @field:Schema(description = "내 식물 학명", example = "몬스테라 델리오사")
    val scientificName: String,
    @field:Schema(description = "마지막 물주기로부터 지난 날짜", example = "2")
    val dateSinceLastWater: Int?,
    @field:Schema(description = "마지막 비료주기로부터 지난 날짜", example = "30")
    val dateSinceLastFertilizer: Int?,
    @field:Schema(description = "마지막 눈길주기로부터 지난 날짜", example = "30")
    val dateSinceLasthealthCheck: Int,
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
                dateSinceLastWater = myPlant.getDateSinceLastWater(now),
                dateSinceLastFertilizer = myPlant.getDateSinceLastFertilizer(now),
                dateSinceLasthealthCheck = myPlant.getDateSinceLastHealthCheck(now),
            )
    }
}
