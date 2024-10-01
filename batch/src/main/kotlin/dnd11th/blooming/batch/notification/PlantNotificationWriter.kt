package dnd11th.blooming.batch.notification

import dnd11th.blooming.client.fcm.FcmService
import dnd11th.blooming.client.fcm.PushNotification
import dnd11th.blooming.common.util.Logger.Companion.log
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@Configuration
class PlantNotificationWriter(
    private val fcmService: FcmService,
) {
    private var counter = AtomicInteger(0)
    private val customDispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

    @Bean
    @StepScope
    fun waterNotificationItemWriter(): ItemWriter<PushNotification> {
        return ItemWriter { pushNotifications ->
            runBlocking {
                pushNotifications.forEach { pushNotification ->
                    launch(customDispatcher) {
                        val currentCount = counter.incrementAndGet()
                        val threadName = Thread.currentThread().name
                        fcmService.mock(pushNotification)
                        log.info { "Thread: $threadName, Count: $currentCount" }
                    }
                }
            }
        }
    }
}
