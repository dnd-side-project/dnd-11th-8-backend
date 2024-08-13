package dnd11th.blooming.api.dto.home

import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "MyPlant Home Response",
    description = "홈화면에서의 내 식물 정보 응답",
)
data class MyPlantHomeResponse(
    @field:Schema(name = "내 식물 ID", example = "1")
    val myPlantId: Long,
    @field:Schema(name = "내 식물 별명", example = "뿡뿡이")
    val nickname: String,
    @field:Schema(name = "내 식물 학명", example = "몬스테라 델리오사")
    val scientificName: String,
    @field:Schema(name = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean,
    @field:Schema(name = "다음 물주기까지 남은 날짜", example = "2")
    val waterRemainDay: Int?,
    @field:Schema(name = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean,
    @field:Schema(name = "다음 비료주기까지 남은 날짜", example = "30")
    val fertilizerRemainDay: Int?,
    @field:Schema(name = "건강확인 알림 여부", example = "true")
    val healthCheckAlarm: Boolean,
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
                waterAlarm = myPlant.alarm.waterAlarm,
                waterRemainDay = myPlant.getWaterRemainDay(now),
                fertilizerAlarm = myPlant.alarm.fertilizerAlarm,
                fertilizerRemainDay = myPlant.getFerilizerRemainDate(now),
                healthCheckAlarm = myPlant.alarm.healthCheckAlarm,
            )
    }
}
