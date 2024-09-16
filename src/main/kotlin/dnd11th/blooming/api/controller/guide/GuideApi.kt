package dnd11th.blooming.api.controller.guide

import dnd11th.blooming.api.dto.guide.PlantGuideResponse
import dnd11th.blooming.api.dto.guide.PlantRecommendedPeriodResponse
import dnd11th.blooming.api.dto.guide.PlantResponse
import dnd11th.blooming.api.annotation.ApiErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "4. [가이드]")
interface GuideApi {
    @Operation(summary = "식물 종류를 검색하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "식물 종류 검색 성공")
    fun findPlantList(
        @Parameter(description = "식물 이름", required = true)
        plantName: String,
    ): List<PlantResponse>

    @Operation(summary = "식물 가이드를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "식물 가이드 조회 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_PLANT, "id에 해당하는 식물을 찾지 못했을 때 에러입니다.")
    fun findPlantGuide(
        @Parameter(description = "식물 ID", required = true)
        plantId: Long,
    ): PlantGuideResponse

    @Operation(summary = "식물의 권장 물주기/비료주기 권장 주기를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "식물 가이드 조회 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_PLANT, "id에 해당하는 식물을 찾지 못했을 때 에러입니다.")
    fun findPlantRecommendedPeriod(
        @Parameter(description = "식물 ID", required = true)
        plantId: Long,
    ): PlantRecommendedPeriodResponse
}
