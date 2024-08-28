package dnd11th.blooming.batch

import dnd11th.blooming.client.fcm.FcmService
import dnd11th.blooming.client.fcm.PushNotification
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlantNotificationWriter(
    private val fcmService: FcmService,
) {
    @StepScope
    @Bean
    fun waterNotificationItemWriter(): ItemWriter<PushNotification> {
        return ItemWriter { pushNotifications ->
            pushNotifications.forEach { pushNotification ->
                fcmService.send(pushNotification)
            }
        }
    }
}
