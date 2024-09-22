package dnd11th.blooming.batch.controller

import dnd11th.blooming.batch.weather.WeatherCareMessageScheduler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DailyWeatherCallController(
    private val weatherCareMessageScheduler: WeatherCareMessageScheduler
) {
    @GetMapping("/daily-weather-call")
    fun runWeatherCareMessageJob(): ResponseEntity<Void> {
        weatherCareMessageScheduler.run()
        return ResponseEntity.noContent().build()
    }
}
