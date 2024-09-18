package dnd11th.blooming.api.controller.region

import dnd11th.blooming.api.dto.region.RegionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "7. [지역]")
interface RegionApi {
    @Operation(summary = "지역을 검색하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "지역 검색 성공")
    fun findRegion(
        @Parameter(description = "지역 이름", required = true)
        name: String,
    ): List<RegionResponse>
}
