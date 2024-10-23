package dnd11th.blooming.batch.notification

import dnd11th.blooming.client.expo.PushNotification
import dnd11th.blooming.client.expo.PushService
import dnd11th.blooming.common.util.Logger.Companion.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
class PlantNotificationWriter(
    private val pushService: PushService,
) {
    private val customDispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

    @Bean
    @StepScope
    fun waterNotificationItemWriter(): ItemWriter<PushNotification> {
        val scope = CoroutineScope(customDispatcher)
        return ItemWriter { chunk ->
            val pushNotifications = chunk.toList()
            scope.launch {
                try {
                    val threadName = Thread.currentThread().name
                    pushService.mock(pushNotifications)
                    log.info { "Thread: $threadName, Sent ${pushNotifications.size} notifications" }
                } catch (e: Exception) {
                    log.error(e) { "${e.message}" }
                }
            }
        }
    }
}
