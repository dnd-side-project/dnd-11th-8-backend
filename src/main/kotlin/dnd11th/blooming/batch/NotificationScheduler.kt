package dnd11th.blooming.batch

import dnd11th.blooming.common.util.Logger.Companion.log
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import java.util.concurrent.TimeUnit

@Component
class NotificationScheduler(
    private val jobLauncher: JobLauncher,
    private val notificationJob: Job,
) {
    @Scheduled(cron = "-1 -1 -1 * * *")
    fun run() {
        val jobParameters: JobParameters =
            JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()
        val stopWatch = StopWatch()
        stopWatch.start()
        jobLauncher.run(notificationJob, jobParameters)
        stopWatch.stop()
        log.info { stopWatch.getTotalTime(TimeUnit.MILLISECONDS) }
    }
}
