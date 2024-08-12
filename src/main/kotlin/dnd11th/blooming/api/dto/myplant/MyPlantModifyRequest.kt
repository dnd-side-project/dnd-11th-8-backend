package dnd11th.blooming.api.dto.myplant

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import java.time.LocalDate

data class MyPlantModifyRequest(
    @field:NotBlank(message = "식물 별명은 비어있을 수 없습니다.")
    val nickname: String?,
    val locationId: Long?,
    @field:PastOrPresent(message = "키우기 시작한 날짜는 미래일 수 없습니다.")
    val startDate: LocalDate?,
    @field:PastOrPresent(message = "마지막으로 물 준 날짜는 미래일 수 없습니다.")
    val lastWateredDate: LocalDate?,
    @field:PastOrPresent(message = "마지막으로 비료 준 날짜는 미래일 수 없습니다.")
    val lastFertilizerDate: LocalDate?,
)
