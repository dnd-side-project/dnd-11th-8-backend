package dnd11th.blooming.batch.controller

import dnd11th.blooming.batch.notification.NotificationScheduler
import dnd11th.blooming.batch.weather.WeatherCareMessageScheduler
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchCallController(
    private val weatherCareMessageScheduler: WeatherCareMessageScheduler,
    private val notificationScheduler: NotificationScheduler,
) {
    @GetMapping("/daily-weather-call")
    fun runWeatherCareMessageJob(): ResponseEntity<Void> {
        weatherCareMessageScheduler.run()
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/notification-call")
    fun runNotificationJob(): ResponseEntity<Void> {
        notificationScheduler.run()
        return ResponseEntity.noContent().build()
    }
}
