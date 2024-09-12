package dnd11th.blooming.batch

import dnd11th.blooming.client.fcm.FcmService
import dnd11th.blooming.client.fcm.PushNotification
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlantNotificationWriter(
    private val fcmService: FcmService,
) {
    @Bean
    @StepScope
    fun waterNotificationItemWriter(): ItemWriter<PushNotification> {
        return ItemWriter { pushNotifications ->
            runBlocking {
                pushNotifications.forEach { pushNotification ->
                    launch {
                        fcmService.send(pushNotification) // 비동기 처리
                    }
                }
            }
        }
    }
}
