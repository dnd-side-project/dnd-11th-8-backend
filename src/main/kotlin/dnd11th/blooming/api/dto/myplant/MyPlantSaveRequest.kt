package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "MyPlant Save Request",
    description = "내 식물 저장 요청",
)
data class MyPlantSaveRequest(
    @field:Schema(name = "식물 ID", example = "3")
    val scientificName: String,
    @field:Schema(name = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(name = "위치 ID", example = "4")
    val locationId: Long,
    @field:Schema(name = "키우기 시작한 날짜", example = "2024-05-17")
    val startDate: LocalDate,
    @field:Schema(name = "마지막으로 물 준 날짜", example = "2024-05-17")
    val lastWateredDate: LocalDate,
    @field:Schema(name = "마지막으로 비료 준 날짜", example = "2024-05-17")
    val lastFertilizerDate: LocalDate,
    @field:Schema(name = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean,
    @field:Schema(name = "물주기 알림 주기", example = "15")
    val waterPeriod: Int?,
    @field:Schema(name = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean,
    @field:Schema(name = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @field:Schema(name = "건강확인 알림 여부", example = "true")
    val healthCheckAlarm: Boolean,
) {
    fun toMyPlant(
        location: Location,
        now: LocalDate,
    ): MyPlant =
        MyPlant(
            scientificName = scientificName,
            nickname = nickname,
            startDate = startDate,
            lastWateredDate = lastWateredDate,
            lastFertilizerDate = lastFertilizerDate,
            currentDate = now,
            alarm =
                Alarm(
                    waterAlarm = waterAlarm,
                    waterPeriod = waterPeriod,
                    fertilizerAlarm = fertilizerAlarm,
                    fertilizerPeriod = fertilizerPeriod,
                    healthCheckAlarm = healthCheckAlarm,
                ),
        ).also {
            it.setLocationRelation(location)
            // TODO : 유저와 매핑 필요
            // TODO : 식물가이드와 매핑 필요
        }
}
