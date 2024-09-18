package dnd11th.blooming.api.dto.myplant

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.PastOrPresent
import org.hibernate.validator.constraints.Range
import java.time.LocalDate

@Schema(
    name = "MyPlant Modify Request",
    description = "내 식물 수정 요청",
)
data class MyPlantModifyRequest(
    @field:Schema(description = "변경할 식물 별명", example = "쫑쫑이")
    val nickname: String?,
    @field:Schema(description = "변경할 위치 ID", example = "4")
    val locationId: Long?,
    @field:Schema(description = "변경할 키우기 시작한 날짜", example = "2024-05-17")
    @field:PastOrPresent(message = "키우기 시작한 날짜는 미래일 수 없습니다.")
    val startDate: LocalDate?,
    @field:Schema(description = "마지막으로 물 준 날짜 선택지 번호", example = "3")
    @field:Range(min = 1, max = 6, message = "1에서 6의 값을 입력하세요.")
    val lastWateredDate: Int?,
    @field:Schema(description = "마지막으로 비료 준 날짜 선택지 번호", example = "2")
    @field:Range(min = 1, max = 6, message = "1에서 6의 값을 입력하세요.")
    val lastFertilizerDate: Int?,
)
