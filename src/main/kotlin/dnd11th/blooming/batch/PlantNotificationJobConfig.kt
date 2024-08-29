package dnd11th.blooming.batch

import dnd11th.blooming.client.fcm.PushNotification
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class PlantNotificationJobConfig(
    private val transactionManager: PlatformTransactionManager,
    private val waterNotificationItemReader: ItemReader<UserPlantDto>,
    private val waterNotificationItemProcessor: ItemProcessor<UserPlantDto, PushNotification>,
    private val waterNotificationItemWriter: ItemWriter<PushNotification>,
) {
    companion object {
        const val CHUNK_SIZE: Int = 10
    }

    @Bean
    fun notificationJob(jobRepository: JobRepository): Job {
        return JobBuilder("notificationJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(waterNotificationStep(jobRepository))
            .build()
    }

    @JobScope
    @Bean
    fun waterNotificationStep(jobRepository: JobRepository): Step {
        return StepBuilder("waterNotificationStep", jobRepository)
            .chunk<UserPlantDto, PushNotification>(CHUNK_SIZE, transactionManager)
            .reader(waterNotificationItemReader)
            .processor(waterNotificationItemProcessor)
            .writer(waterNotificationItemWriter)
            .build()
    }
}
