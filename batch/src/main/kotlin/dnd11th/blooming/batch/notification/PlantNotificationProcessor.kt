package dnd11th.blooming.batch.notification

import dnd11th.blooming.client.expo.PushNotification
import dnd11th.blooming.core.entity.myplant.UserPlantDto
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
            PushNotification.create(userPlantDto.deviceToken, userPlantDto.plantNickname)
        }
    }
}
