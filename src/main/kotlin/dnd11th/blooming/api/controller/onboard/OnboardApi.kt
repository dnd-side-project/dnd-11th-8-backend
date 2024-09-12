package dnd11th.blooming.api.controller.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.ScriptResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "10. [온보딩]")
interface OnboardApi {
    @Operation(summary = "질문지를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "질문지 조회 성공")
    fun findScripts(): List<ScriptResponse>

    @Operation(summary = "응답을 제출하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "응답 제출 성공")
    fun submitScripts(
        @RequestBody(description = "응답 요청", required = true)
        request: List<OnboardResultRequest>,
    ): OnboardResultResponse
}
