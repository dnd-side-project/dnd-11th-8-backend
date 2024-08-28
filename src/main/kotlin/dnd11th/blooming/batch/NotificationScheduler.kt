package dnd11th.blooming.batch

import dnd11th.blooming.domain.entity.user.AlarmTime
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class NotificationScheduler(
    private val notificationJob: Job,
    private val jobLauncher: JobLauncher,
) {
    @Scheduled(cron = "0 0 5-23 * * *")
    fun run() {
        val now: LocalTime = LocalTime.now()
        val alarmTime = AlarmTime.fromHour(now)

        val jobParameters =
            JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("alarmTime", alarmTime.name)
                .toJobParameters()
        jobLauncher.run(notificationJob, jobParameters)
    }
}
