package dnd11th.blooming.api.dto.weather

import dnd11th.blooming.redis.entity.weather.Message
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Weather Message Response",
    description = "날씨 메시지 반환 응답",
)
data class WeatherMessageResponse(
    @field:Schema(name = "status", example = "HUMIDITY")
    val status: String,
    @field:Schema(name = "title", example = "과습주의보")
    val title: String,
    @field:Schema(name = "message", example = "[습도가 높습니다, 주의하세요]")
    val message: List<String>,
) {
    companion object {
        fun from(message: Message): WeatherMessageResponse {
            return WeatherMessageResponse(
                status = message.status,
                title = message.title,
                message = splitByNewLine(message.message),
            )
        }

        private fun splitByNewLine(text: String): List<String> {
            return text.split("\n")
        }
    }
}
