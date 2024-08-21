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
    val myPlantId: Long?,
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(description = "위치 존재 여부", example = "1")
    val haveLocation: Boolean,
    @field:Schema(description = "이미지 URL", example = "image.com/7")
    val imageUrl: String,
    @field:Schema(description = "내 식물 학명", example = "몬스테라 델리오사")
    val scientificName: String,
    @field:Schema(description = "마지막 물주기로부터 지난 날짜", example = "2")
    val dateSinceLastWater: Int?,
    @field:Schema(description = "마지막 비료주기로부터 지난 날짜", example = "30")
    val dateSinceLastFertilizer: Int?,
    @field:Schema(description = "마지막 눈길주기로부터 지난 날짜", example = "30")
    val dateSinceLastHealthCheck: Int,
) {
    companion object {
        fun of(
            myPlant: MyPlant,
            imageUrl: String?,
            now: LocalDate,
        ): MyPlantResponse =
            MyPlantResponse(
                myPlantId = myPlant.id,
                nickname = myPlant.nickname,
                haveLocation = myPlant.location != null,
                imageUrl = imageUrl ?: myPlant.plantImageUrl,
                scientificName = myPlant.scientificName,
                dateSinceLastWater = myPlant.getDateSinceLastWater(now),
                dateSinceLastFertilizer = myPlant.getDateSinceLastFertilizer(now),
                dateSinceLastHealthCheck = myPlant.getDateSinceLastHealthCheck(now),
            )
    }
}
