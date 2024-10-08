package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.core.entity.myplant.MyPlantCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import org.hibernate.validator.constraints.Range
import java.time.LocalDate

@Schema(
    name = "MyPlant Save Request",
    description = "내 식물 저장 요청",
)
data class MyPlantSaveRequest(
    @field:Schema(description = "식물 학명", example = "몬스테라 델리오사")
    @field:NotBlank(message = "식물 학명은 필수값입니다.")
    val scientificName: String?,
    @field:Schema(description = "식물 ID", example = "3")
    val plantId: Long?,
    @field:Schema(description = "내 식물 별명", example = "쫑쫑이")
    val nickname: String?,
    @field:Schema(description = "위치 ID", example = "4")
    val locationId: Long?,
    @field:Schema(description = "키우기 시작한 날짜", example = "2024-05-17")
    @field:PastOrPresent(message = "키우기 시작한 날짜는 미래일 수 없습니다.")
    val startDate: LocalDate?,
    @field:Schema(description = "마지막으로 물 준 날짜 선택지 번호", example = "3")
    @field:NotNull(message = "마지막으로 물 준 날짜는 필수입니다.")
    @field:Range(min = 1, max = 6, message = "1에서 6의 값을 입력하세요.")
    val lastWateredDate: Int?,
    @field:Schema(description = "마지막으로 비료 준 날짜 선택지 번호", example = "2")
    @field:NotNull(message = "마지막으로 비료 준 날짜는 필수입니다.")
    @field:Range(min = 1, max = 6, message = "1에서 6의 값을 입력하세요.")
    val lastFertilizerDate: Int?,
    @field:NotNull(message = "물주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "물주기 알림 여부", example = "true")
    val waterAlarm: Boolean?,
    @field:Schema(description = "물주기 알림 주기", example = "15")
    val waterPeriod: Int?,
    @field:NotNull(message = "비료주기 알림 여부는 필수값입니다.")
    @field:Schema(description = "비료주기 알림 여부", example = "false")
    val fertilizerAlarm: Boolean?,
    @field:Schema(description = "비료주기 알림 주기", example = "45")
    val fertilizerPeriod: Int?,
    @field:NotNull(message = "건강확인 알림 여부는 필수값입니다.")
    @field:Schema(description = "건강확인 알림 여부", example = "true")
    val healthCheckAlarm: Boolean?,
) {
    fun toMyPlantCreateDto(): MyPlantCreateDto =
        MyPlantCreateDto(
            nickname = nickname,
            scientificName = scientificName!!,
            plantId = plantId,
            locationId = locationId,
            startDate = startDate ?: LocalDate.now(),
            lastWateredDate = lastWateredDate!!,
            lastFertilizerDate = lastFertilizerDate!!,
            waterAlarm = waterAlarm!!,
            waterPeriod = waterPeriod,
            fertilizerAlarm = fertilizerAlarm!!,
            fertilizerPeriod = fertilizerPeriod,
            healthCheckAlarm = healthCheckAlarm!!,
        )
}
