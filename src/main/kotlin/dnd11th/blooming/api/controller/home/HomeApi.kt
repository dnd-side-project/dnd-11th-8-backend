package dnd11th.blooming.api.controller.home

import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.domain.core.entity.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "3. [홈 화면]")
interface HomeApi {
    @Operation(summary = "홈 화면의 정보들을 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "홈 화면 조회 성공")
    fun getHome(
        @Schema(hidden = true)
        user: User,
    ): HomeResponse
}
