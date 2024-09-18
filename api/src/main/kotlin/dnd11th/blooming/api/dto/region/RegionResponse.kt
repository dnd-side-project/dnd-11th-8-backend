package dnd11th.blooming.api.dto.region

import dnd11th.blooming.core.entity.region.Region
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Region Response",
    description = "지역 리스트 결과 응답",
)
data class RegionResponse(
    @field:Schema(name = "id", example = "3")
    val id: Int,
    @field:Schema(name = "name", example = "서울특별시 송파구 가락2동")
    val name: String,
) {
    companion object {
        fun from(region: Region): RegionResponse {
            return RegionResponse(region.id, region.name)
        }
    }
}
