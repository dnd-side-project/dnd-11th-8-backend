package dnd11th.blooming.api.dto.myplant

import io.swagger.v3.oas.annotations.media.Schema
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
    val startDate: LocalDate?,
    @field:Schema(description = "변경할 마지막으로 물 준 날짜", example = "2024-05-17")
    val lastWateredDate: LocalDate?,
    @field:Schema(description = "변경할 마지막으로 비료 준 날짜", example = "2024-05-17")
    val lastFertilizerDate: LocalDate?,
)
