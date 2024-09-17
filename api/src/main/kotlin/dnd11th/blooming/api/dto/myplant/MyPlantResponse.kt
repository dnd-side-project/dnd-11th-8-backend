package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.core.entity.myplant.MyPlant
import dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

@Schema(
    name = "MyPlant Response",
    description = "내 식물 응답",
)
data class MyPlantResponse(
    @field:Schema(description = "내 식물 ID", example = "17")
    val myPlantId: Long?,
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(description = "위치 ID", example = "1")
    val locationId: Long?,
    @field:Schema(description = "등록 일자", example = "2024-09-03T14:30:45")
    val registeredDateTime: LocalDateTime,
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
                locationId = myPlant.location?.id,
                registeredDateTime = myPlant.createdDate,
                imageUrl = imageUrl ?: myPlant.plantImageUrl,
                scientificName = myPlant.scientificName,
                dateSinceLastWater = myPlant.getDateSinceLastWater(now),
                dateSinceLastFertilizer = myPlant.getDateSinceLastFertilizer(now),
                dateSinceLastHealthCheck = myPlant.getDateSinceLastHealthCheck(now),
            )

        fun fromMyPlantWithImageUrlList(
            myPlantWithImageUrlList: List<MyPlantWithImageUrl>,
            now: LocalDate,
        ): List<MyPlantResponse> = myPlantWithImageUrlList.map { dto -> of(dto.myPlant, dto.imageUrl, now) }
    }
}
