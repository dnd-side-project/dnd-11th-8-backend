package dnd11th.blooming.api.controller.weather

import dnd11th.blooming.api.annotation.LoginUser
import dnd11th.blooming.api.annotation.Secured
import dnd11th.blooming.api.dto.weather.WeatherMessageResponse
import dnd11th.blooming.api.service.weather.WeatherService
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.redis.entity.weather.Message
import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
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
        val weatherCareMessage: WeatherCareMessage =
            weatherService.createWeatherMessage(loginUser, LocalDateTime.now())
        val messages: List<Message> = weatherCareMessage.messages
        return messages.map { weatherMessage -> WeatherMessageResponse.from(weatherMessage) }
    }
}
