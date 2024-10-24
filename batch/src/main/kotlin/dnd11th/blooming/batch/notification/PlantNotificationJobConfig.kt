package dnd11th.blooming.batch.notification

import dnd11th.blooming.client.expo.PushNotification
import dnd11th.blooming.core.entity.myplant.UserPlantDto
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
import org.springframework.context.annotation.Primary
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class PlantNotificationJobConfig {
    companion object {
        const val CHUNK_SIZE: Int = 100
    }

    @Bean
    @Primary
    fun notificationJob(
        jobRepository: JobRepository,
        waterNotificationStep: Step,
        fertilizerNotificationStep: Step,
        healthCheckNotificationStep: Step,
    ): Job {
        return JobBuilder("notificationJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(waterNotificationStep)
            .next(fertilizerNotificationStep)
            .next(healthCheckNotificationStep)
            .build()
    }

    @Bean
    @JobScope
    fun waterNotificationStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        waterNotificationItemReader: ItemReader<UserPlantDto>,
        waterNotificationItemProcessor: ItemProcessor<UserPlantDto, PushNotification>,
        waterNotificationItemWriter: ItemWriter<PushNotification>,
    ): Step {
        return StepBuilder("waterNotificationStep", jobRepository)
            .chunk<UserPlantDto, PushNotification>(CHUNK_SIZE, transactionManager)
            .reader(waterNotificationItemReader)
            .processor(waterNotificationItemProcessor)
            .writer(waterNotificationItemWriter)
            .build()
    }

    @Bean
    @JobScope
    fun fertilizerNotificationStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        fertilizerNotificationItemReader: ItemReader<UserPlantDto>,
        waterNotificationItemProcessor: ItemProcessor<UserPlantDto, PushNotification>,
        waterNotificationItemWriter: ItemWriter<PushNotification>,
    ): Step {
        return StepBuilder("fertilizerNotificationStep", jobRepository)
            .chunk<UserPlantDto, PushNotification>(CHUNK_SIZE, transactionManager)
            .reader(fertilizerNotificationItemReader)
            .processor(waterNotificationItemProcessor)
            .writer(waterNotificationItemWriter)
            .build()
    }

    @Bean
    @JobScope
    fun healthCheckNotificationStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        healthCheckNotificationItemReader: ItemReader<UserPlantDto>,
        waterNotificationItemProcessor: ItemProcessor<UserPlantDto, PushNotification>,
        waterNotificationItemWriter: ItemWriter<PushNotification>,
    ): Step {
        return StepBuilder("healthCheckNotificationStep", jobRepository)
            .chunk<UserPlantDto, PushNotification>(CHUNK_SIZE, transactionManager)
            .reader(healthCheckNotificationItemReader)
            .processor(waterNotificationItemProcessor)
            .writer(waterNotificationItemWriter)
            .build()
    }
}
