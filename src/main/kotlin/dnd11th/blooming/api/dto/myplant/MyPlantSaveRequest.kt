package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class MyPlantSaveRequest(
    // TODO : 식물 종류를 String이 아니라 plantId로 받기
    @NotNull(message = "식물 종류는 필수값입니다.")
    @NotBlank(message = "식물 종류는 비어있을 수 없습니다.")
    val scientificName: String,
    val nickname: String = scientificName,
    @NotNull(message = "위치는 필수값입니다.")
    val locationId: Long,
    // TODO : 키우기 시작한 날짜, 마지막으로 물 준 날짜, 마지막으로 비료 준 날짜 optional로 만들기
    val startDate: LocalDate,
    val lastWateredDate: LocalDate,
    val lastFertilizerDate: LocalDate,
    @NotNull(message = "물주기 알림 여부는 필수값입니다.")
    val waterAlarm: Boolean,
    val waterPeriod: Int?,
    @NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    val fertilizerAlarm: Boolean,
    val fertilizerPeriod: Int?,
    @NotNull(message = "건강확인 알림 여부는 필수값입니다.")
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
