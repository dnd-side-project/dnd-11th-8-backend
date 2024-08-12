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
    // TODO : 식물 종류를 String이 아니라 plantId로 받기
	@field:Schema(description = "식물 ID", example = "3")
    @field:NotNull(message = "식물 종류는 필수값입니다.")
    @field:NotBlank(message = "식물 종류는 비어있을 수 없습니다.")
    val scientificName: String,
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String,
    @field:Schema(description = "위치 ID", example = "4")
    @field:NotNull(message = "위치는 필수값입니다.")
    val locationId: Long,
    @field:Schema(description = "키우기 시작한 날짜", example = "2024-05-17")
    val startDate: LocalDate,
    @field:Schema(description = "마지막으로 물 준 날짜", example = "2024-05-17")
    val lastWateredDate: LocalDate,
    @field:Schema(description = "마지막으로 비료 준 날짜", example = "2024-05-17")
    val lastFertilizerDate: LocalDate,
    @field:NotNull(message = "물주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean,
    @field:Schema(description = "물주기 알림 주기", example = "15")
    val waterPeriod: Int?,
    @field:NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean,
    @field:Schema(description = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    @field:Schema(description = "건강확인 알림 여부", example = "true")
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
