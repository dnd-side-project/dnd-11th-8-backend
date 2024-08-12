package dnd11th.blooming.api.dto.weather

import dnd11th.blooming.api.service.weather.WeatherMessage
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Weather Message Response",
    description = "날씨 메시지 반환 응답",
)
data class WeatherMessageResponse(
    @Schema(name = "status", example = "HUMIDITY")
    val status: String,
    @Schema(name = "title", example = "과습주의보")
    val title: String,
    @Schema(name = "message", example = "[습도가 높습니다, 주의하세요]")
    val message: List<String>,
) {
    companion object {
        fun from(weatherMessages: WeatherMessage): WeatherMessageResponse {
            return WeatherMessageResponse(
                status = weatherMessages.name,
                title = weatherMessages.title,
                message = weatherMessages.message,
            )
        }
    }
}
