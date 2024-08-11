package dnd11th.blooming.api.controller.weather

import dnd11th.blooming.api.dto.weather.WeatherMessageResponse
import dnd11th.blooming.api.service.weather.WeatherMessage
import dnd11th.blooming.api.service.weather.WeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/weather-message")
class WeatherMessageController(
    private val weatherService: WeatherService,
) {
    @GetMapping
    fun getWeatherMessage(): List<WeatherMessageResponse> {
        val weatherMessages: List<WeatherMessage> =
            weatherService.createWeatherMessage(LocalDateTime.now())
        return weatherMessages.map { weatherMessage -> WeatherMessageResponse.from(weatherMessage) }
    }
}