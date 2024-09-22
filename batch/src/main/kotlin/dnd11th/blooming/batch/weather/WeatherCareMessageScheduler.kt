package dnd11th.blooming.batch.weather

import dnd11th.blooming.common.util.Logger.Companion.log
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.TimeUnit

@Component
class WeatherCareMessageScheduler(
    private val jobLauncher: JobLauncher,
    @Qualifier("weatherCareMessageBatchJob")
    private val weatherCareMessageJob: Job,
) {

    fun run() {
        val jobParameters: JobParameters =
            JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()
        val stopWatch = StopWatch()
        stopWatch.start()
        jobLauncher.run(weatherCareMessageJob, jobParameters)
        stopWatch.stop()
        log.info { stopWatch.getTotalTime(TimeUnit.MILLISECONDS) }
    }
}
