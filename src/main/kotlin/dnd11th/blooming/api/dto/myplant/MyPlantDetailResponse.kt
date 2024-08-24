package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.api.dto.image.ImageResponse
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.service.myplant.MyPlantMessageFactory
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.Period

@Schema(
    name = "MyPlant Detail Response",
    description = "내 식물 상세조회 응답",
)
data class MyPlantDetailResponse(
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(description = "내 식물 학명", example = "몬스테라 델리오사")
    val scientificName: String,
    @field:Schema(description = "식물 ID", example = "7")
    val plantId: Long?,
    @field:Schema(description = "내 식물 위치")
    val location: LocationResponse?,
    @field:Schema(description = "함께한지 N일째", example = "13")
    val withDays: Int,
    @field:Schema(description = "마지막으로 물 준 날짜 컴포넌트 제목", example = "마지막으로 물 준 날")
    val lastWateredTitle: String,
    @field:Schema(description = "마지막으로 물 준 날짜 컴포넌트 내용", example = "2024-05-17\n17일전")
    val lastWateredInfo: String,
    @field:Schema(description = "마지막으로 물 준 날짜", example = "2024-05-17")
    val lastWateredDate: LocalDate?,
    @field:Schema(description = "마지막으로 비료 준 날짜 컴포넌트 제목", example = "비료주기")
    val lastFertilizerTitle: String,
    @field:Schema(description = "마지막으로 비료 준 날짜 컴포넌트 내용", example = "17일전")
    val lastFertilizerInfo: String,
    @field:Schema(description = "마지막으로 비료 준 날짜", example = "2024-05-17")
    val lastFertilizerDate: LocalDate?,
    @field:Schema(description = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean,
    @field:Schema(description = "물주기 알림 주기", example = "4")
    val waterPeriod: Int?,
    @field:Schema(description = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean,
    @field:Schema(description = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @field:Schema(description = "건강확인 알림 여부", example = "true")
    val healthCheckAlarm: Boolean,
    @field:Schema(description = "내 식물 이미지들")
    val images: List<ImageResponse>,
) {
    companion object {
        fun of(
            myPlant: MyPlant,
            messageFactory: MyPlantMessageFactory,
            images: List<Image>,
            now: LocalDate,
        ): MyPlantDetailResponse =
            MyPlantDetailResponse(
                nickname = myPlant.nickname,
                scientificName = myPlant.scientificName,
                plantId = myPlant.plant?.id,
                location = myPlant.location?.let { LocationResponse.from(it) },
                withDays = Period.between(myPlant.startDate, now).days,
                lastWateredTitle = messageFactory.createWateredTitle(),
                lastWateredInfo = messageFactory.createWateredInfo(myPlant.lastWateredDate, now),
                lastWateredDate = myPlant.lastWateredDate,
                lastFertilizerTitle = messageFactory.createFertilizerTitle(),
                lastFertilizerInfo = messageFactory.createFertilizerInfo(myPlant.lastFertilizerDate, now),
                lastFertilizerDate = myPlant.lastFertilizerDate,
                waterAlarm = myPlant.alarm.waterAlarm,
                waterPeriod = myPlant.alarm.waterPeriod,
                fertilizerAlarm = myPlant.alarm.fertilizerAlarm,
                fertilizerPeriod = myPlant.alarm.fertilizerPeriod,
                healthCheckAlarm = myPlant.alarm.healthCheckAlarm,
                images = ImageResponse.fromList(images),
            )
    }
}
