package dnd11th.blooming.api.controller.location

import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.common.annotation.ApiErrorResponse
import dnd11th.blooming.common.annotation.ApiErrorResponses
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.domain.entity.user.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "5. [위치]")
interface LocationApi {
    @Operation(summary = "위치를 저장하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "위치 저장 성공")
    @ApiErrorResponse(errorType = ErrorType.BAD_REQUEST, description = "요청의 name이 null이거나 비어있을 때 에러입니다.")
    fun saveLocation(
        @RequestBody(description = "위치 저장 요청", required = true)
        request: LocationSaveRequest,
        @Schema(hidden = true)
        user: User,
    ): LocationSaveResponse

    @Operation(summary = "전체 위치들을 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "위치 전체 조회 성공")
    fun findAllLocation(
        @Schema(hidden = true)
        user: User,
    ): List<LocationResponse>

    @Operation(summary = "위치를 수정하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "위치 수정 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NOT_FOUND_LOCATION, "id에 해당하는 위치를 찾지 못했을 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 name이 null이거나 비어있을 때 에러입니다."),
        ],
    )
    fun modifyLocation(
        @Parameter(description = "위치 ID", required = true)
        locationId: Long,
        @RequestBody(description = "이미지 수정 요청", required = true)
        request: LocationModifyRequest,
        @Schema(hidden = true)
        user: User,
    ): LocationResponse

    @Operation(summary = "위치를 삭제하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "위치 삭제 성공")
    @ApiErrorResponse(errorType = ErrorType.NOT_FOUND_LOCATION, description = "id에 해당하는 위치를 찾지 못했을 때 에러입니다.")
    fun deleteLocation(
        @Parameter(description = "위치 ID", required = true)
        locationId: Long,
        @Schema(hidden = true)
        user: User,
    )
}
