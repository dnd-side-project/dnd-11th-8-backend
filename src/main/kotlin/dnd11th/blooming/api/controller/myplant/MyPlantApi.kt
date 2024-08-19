package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.HealthCheckResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDirectionParam
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSortParam
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

@Tag(name = "3. [내 식물]")
interface MyPlantApi {
    @Operation(summary = "내 식물을 저장하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 저장 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 plantId가 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 nickname이 null이거나 비어있을 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 locationId가 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 startDate가 미래일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 lastWateredDate가 미래일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 lastFertilizerDate가 미래일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 waterAlarm이 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 fertilizerAlarm이 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 healthCheckAlarm이 null일 때 에러입니다."),
        ],
    )
    fun saveMyPlant(
        @RequestBody(description = "내 식물 저장 요청", required = true)
        request: MyPlantSaveRequest,
        @Schema(hidden = true)
        user: User,
    ): MyPlantSaveResponse

    @Operation(summary = "내 전체 식물들을 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 전체 조회 성공")
    fun findAllMyPlant(
        @Parameter(description = "위치 ID", required = false)
        locationId: Long?,
        @Parameter(description = "정렬 기준", required = false)
        sort: MyPlantSortParam,
        @Parameter(description = "정렬 순서", required = false)
        direction: MyPlantDirectionParam,
        @Schema(hidden = true)
        user: User,
    ): List<MyPlantResponse>

    @Operation(summary = "내 식물의 상세정보를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 상세 조회 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다.")
    fun findMyPlantDetail(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @Schema(hidden = true)
        user: User,
    ): MyPlantDetailResponse

    @Operation(summary = "내 식물을 수정하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 수정 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다."),
            ApiErrorResponse(ErrorType.NOT_FOUND_LOCATION, "요청의 locationId에 해당하는 위치를 찾지 못했을 때 예외입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 startDate가 미래일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 lastWateredDate가 미래일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 lastFertilizerDate가 미래일 때 에러입니다."),
        ],
    )
    fun modifyMyPlant(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @RequestBody(description = "내 식물 수정 요청", required = true)
        request: MyPlantModifyRequest,
        @Schema(hidden = true)
        user: User,
    )

    @Operation(summary = "내 식물을 삭제하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 삭제 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다.")
    fun deleteMyPlant(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @Schema(hidden = true)
        user: User,
    )

    @Operation(summary = "내 식물에 물을 주는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 물주기 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다.")
    fun waterMyPlant(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @Schema(hidden = true)
        user: User,
    )

    @Operation(summary = "내 식물을 비료를 주는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 비료주기 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다.")
    fun fertilizerMyPlant(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @Schema(hidden = true)
        user: User,
    )

    @Operation(summary = "내 식물을 살펴보기를 하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 살펴보기 성공")
    @ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다.")
    fun healthCheckMyPlant(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @Schema(hidden = true)
        user: User,
    ): HealthCheckResponse

    @Operation(summary = "내 식물의 알림을 수정하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "내 식물 알림 수정 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NOT_FOUND_MYPLANT, "id에 해당하는 내 식물을 찾지 못했을 때 예외입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 waterAlarm이 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 fertilizerAlarm이 null일 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 healthCheckAlarm이 null일 때 에러입니다."),
        ],
    )
    fun modifyMyPlantAlarm(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @RequestBody(description = "이미지 저장 요청", required = true)
        request: AlarmModifyRequest,
        @Schema(hidden = true)
        user: User,
    )
}
