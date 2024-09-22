package dnd11th.blooming.api.controller.weather

import dnd11th.blooming.api.annotation.LoginUser
import dnd11th.blooming.api.annotation.Secured
import dnd11th.blooming.api.dto.weather.WeatherMessageResponse
import dnd11th.blooming.redis.entity.WeatherCareMessageType
import dnd11th.blooming.api.service.weather.WeatherService
import dnd11th.blooming.core.entity.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/weather-message")
class WeatherMessageController(
    private val weatherService: WeatherService,
) : WeatherMessageApi {
    @Secured
    @GetMapping
    override fun getWeatherMessage(
        @LoginUser loginUser: User,
    ): List<WeatherMessageResponse> {
        val weatherCareMessageTypes: List<WeatherCareMessageType> =
            weatherService.createWeatherMessage(loginUser, LocalDateTime.now())
        return weatherCareMessageTypes.map { weatherMessage -> WeatherMessageResponse.from(weatherMessage) }
    }
}
