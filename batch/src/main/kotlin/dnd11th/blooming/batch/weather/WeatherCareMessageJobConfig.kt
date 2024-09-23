package dnd11th.blooming.batch.weather

import dnd11th.blooming.core.entity.region.Region
import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class WeatherCareMessageJobConfig {
    companion object {
        const val CHUNK_SIZE: Int = 1000
    }

    @Bean
    fun weatherCareMessageBatchJob(
        jobRepository: JobRepository,
        weatherCareMessageBatchStep: Step,
    ): Job {
        return JobBuilder("weatherBatchJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(weatherCareMessageBatchStep)
            .build()
    }

    @Bean
    @JobScope
    fun weatherCareMessageBatchStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        weatherCareMessageItemReader: ItemReader<Region>,
        weatherCareMessageItemWriter: ItemWriter<Region>,
    ): Step {
        return StepBuilder("weatherBatchStep", jobRepository)
            .chunk<Region, Region>(CHUNK_SIZE, transactionManager)
            .reader(weatherCareMessageItemReader)
            .writer(weatherCareMessageItemWriter)
            .build()
    }
}
