package dnd11th.blooming.api.controller.onboard

import dnd11th.blooming.api.dto.onboard.ScriptResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "10. [온보딩]")
interface OnboardApi {
    @Operation(summary = "질문지를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "질문지 조회 성공")
    fun findScripts(): List<ScriptResponse>
}
