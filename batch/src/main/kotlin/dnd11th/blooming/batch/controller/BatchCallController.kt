package dnd11th.blooming.batch.controller

import dnd11th.blooming.batch.notification.NotificationScheduler
import dnd11th.blooming.batch.weather.WeatherCareMessageScheduler
import dnd11th.blooming.client.fcm.FcmService
import dnd11th.blooming.client.fcm.PushNotification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BatchCallController(
    private val weatherCareMessageScheduler: WeatherCareMessageScheduler,
    private val notificationScheduler: NotificationScheduler,
    private val fcmService: FcmService
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

    @PostMapping("/call")
    fun noti(): ResponseEntity<Void> {
        fcmService.send(PushNotification(
            1L, "fg796W5XLrghxQxcC2F6cO:APA91bEjiLz59Y7_7a1xuU0pGycDMk6JZ8Y2JfNT6oIpnIBLIVneag3jswbJF-VTCFzRhtXAN4ty2kUefDsODutc9trrHxMcgnrJ801pKtQjj35BBg0yJFcKjsbiFEa007D9qO_SB_UJ",
            "몬스테라", "물주세요"
        ))
        return ResponseEntity.noContent().build()
    }
}
