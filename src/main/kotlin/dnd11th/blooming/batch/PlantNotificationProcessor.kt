package dnd11th.blooming.batch

import dnd11th.blooming.client.fcm.PushNotification
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlantNotificationProcessor {
    @Bean
    @StepScope
    fun waterNotificationItemProcessor(): ItemProcessor<UserPlantDto, PushNotification> {
        return ItemProcessor { userPlantDto ->
            PushNotification.create(userPlantDto)
        }
    }
}
