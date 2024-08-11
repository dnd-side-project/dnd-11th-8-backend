package dnd11th.blooming.api.dto.weather

import dnd11th.blooming.api.service.weather.WeatherMessage

data class WeatherMessageResponse(
    val status: String,
    val title: String,
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
