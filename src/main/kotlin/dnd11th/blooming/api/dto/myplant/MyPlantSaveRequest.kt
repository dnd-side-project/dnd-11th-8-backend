package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class MyPlantSaveRequest(
    // TODO : 식물 종류를 String이 아니라 plantId로 받기
    @field:NotNull(message = "식물 종류는 필수값입니다.")
    val plantId: Long?,
    @field:NotNull(message = "식물 별명은 필수값입니다.")
    @field:NotBlank(message = "식물 별명은 비어있을 수 없습니다.")
    val nickname: String?,
    @field:NotNull(message = "위치는 필수값입니다.")
    val locationId: Long?,
    // TODO : 키우기 시작한 날짜, 마지막으로 물 준 날짜, 마지막으로 비료 준 날짜 optional로 만들기
    @field:PastOrPresent(message = "키우기 시작한 날짜는 미래일 수 없습니다.")
    val startDate: LocalDate,
    @field:PastOrPresent(message = "마지막으로 물 준 날짜는 미래일 수 없습니다.")
    val lastWateredDate: LocalDate,
    @field:PastOrPresent(message = "마지막으로 비료 준 날짜는 미래일 수 없습니다.")
    val lastFertilizerDate: LocalDate,
    @field:NotNull(message = "물주기 알림 여부는 필수값입니다.")
    val waterAlarm: Boolean?,
    val waterPeriod: Int?,
    @field:NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    val fertilizerAlarm: Boolean?,
    val fertilizerPeriod: Int?,
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    val healthCheckAlarm: Boolean?,
) {
    fun toMyPlant(
        location: Location,
        now: LocalDate,
        scientificName: String,
    ): MyPlant =
        MyPlant(
            scientificName = scientificName,
            nickname = nickname!!,
            startDate = startDate,
            lastWateredDate = lastWateredDate,
            lastFertilizerDate = lastFertilizerDate,
            currentDate = now,
            alarm =
                Alarm(
                    waterAlarm = waterAlarm!!,
                    waterPeriod = waterPeriod,
                    fertilizerAlarm = fertilizerAlarm!!,
                    fertilizerPeriod = fertilizerPeriod,
                    healthCheckAlarm = healthCheckAlarm!!,
                ),
        ).also {
            it.setLocationRelation(location)
        }
}
