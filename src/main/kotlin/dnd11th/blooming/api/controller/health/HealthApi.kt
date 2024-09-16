package dnd11th.blooming.api.controller.health

import dnd11th.blooming.api.annotation.ApiErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "9. [헬스체크]")
interface HealthApi {
    @Operation(summary = "헬스 체크 API 입니다.")
    @ApiResponse(responseCode = "200", description = "서버가 정상적으로 동작할 시 ok를 응답합니다.")
    @ApiErrorResponse(errorType = ErrorType.SERVER_ERROR, description = "서버 에러입니다. 백엔드 긴급호출~")
    fun healthCheck(): ResponseEntity<String>
}
