package dnd11th.blooming.api.controller.weather

import dnd11th.blooming.api.dto.weather.WeatherMessageResponse
import dnd11th.blooming.common.annotation.ApiErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "5. [날씨 메시지]")
interface WeatherMessageApi {

    @Operation(summary = "날씨 메시지를 조회하는 API 입니다.")
    @ApiResponse(responseCode = "200", description = "날씨 메시지 조회 성공")
    @ApiErrorResponse(errorType = ErrorType.OPEN_API_CALL_EXCEPTION, description = "기상청 API 호출에 실패했을 시 에러입니다.")
    fun getWeatherMessage(): List<WeatherMessageResponse>
}
