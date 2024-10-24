package dnd11th.blooming.client.expo

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "expoPushClient", url = "https://exp.host/--/api/v2/push/send")
interface ExpoPushClient {
    @PostMapping(consumes = ["application/json"])
    fun sendPushNotification(
        @RequestBody request: List<PushNotification>,
    ): PushNotificationResponse
}
