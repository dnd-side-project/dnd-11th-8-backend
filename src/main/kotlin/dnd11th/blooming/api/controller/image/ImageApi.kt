package dnd11th.blooming.api.controller.image

import dnd11th.blooming.api.dto.image.ImageFavoriteModifyRequest
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.common.annotation.ApiErrorResponse
import dnd11th.blooming.common.annotation.ApiErrorResponses
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "10. [이미지]")
interface ImageApi {
    @Operation(summary = "이미지를 저장하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 저장 성공")
    @ApiErrorResponse(errorType = ErrorType.BAD_REQUEST, description = "요청의 imageUrl이 null이거나 비어있을 때 에러입니다.")
    fun saveImage(
        @Parameter(description = "내 식물 ID", required = true)
        myPlantId: Long,
        @RequestBody(description = "이미지 저장 요청", required = true)
        request: ImageSaveRequest,
    )

    @Operation(summary = "이미지의 즐겨찾기를 수정하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 즐겨찾기 수정 성공")
    @ApiErrorResponses(
        [
            ApiErrorResponse(ErrorType.NOT_FOUND_IMAGE, "id에 해당하는 이미지를 찾지 못했을 때 에러입니다."),
            ApiErrorResponse(ErrorType.BAD_REQUEST, "요청의 favorite이 null일 때 에러입니다."),
        ],
    )
    fun modifyFavorite(
        @Parameter(description = "이미지 ID", required = true)
        imageId: Long,
        @RequestBody(description = "이미지 즐겨찾기 요청", required = true)
        request: ImageFavoriteModifyRequest,
    )

    @Operation(summary = "이미지를 삭제하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "이미지 삭제 성공")
    @ApiErrorResponse(errorType = ErrorType.NOT_FOUND_IMAGE, description = "id에 해당하는 이미지를 찾지 못했을 때 에러입니다.")
    fun deleteImage(
        @Parameter(description = "이미지 ID", required = true)
        imageId: Long,
    )
}
