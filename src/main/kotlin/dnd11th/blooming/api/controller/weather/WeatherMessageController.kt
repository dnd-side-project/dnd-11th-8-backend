package dnd11th.blooming.api.controller.weather

import dnd11th.blooming.api.dto.weather.WeatherMessageResponse
import dnd11th.blooming.api.service.weather.WeatherMessage
import dnd11th.blooming.api.service.weather.WeatherService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.User
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "5. [날씨 메시지]")
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
        val weatherMessages: List<WeatherMessage> =
            weatherService.createWeatherMessage(loginUser, LocalDateTime.now())
        return weatherMessages.map { weatherMessage -> WeatherMessageResponse.from(weatherMessage) }
    }
}
