package dnd11th.blooming.batch.notification

import dnd11th.blooming.core.entity.myplant.UserPlantDto
import dnd11th.blooming.core.entity.user.AlarmTime
import dnd11th.blooming.core.repository.myplant.MyPlantRepository
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalTime

@Configuration
class PlantNotificationReader(
    private val myPlantRepository: MyPlantRepository,
) {
    @Bean
    @StepScope
    fun waterNotificationItemReader(): ListItemReader<UserPlantDto> {
        val now: LocalTime = LocalTime.now()
        // val alarmTime = AlarmTime.fromHour(now)
        val alarmTime = AlarmTime.TIME_10_11

        val userPlantByAlarmTime: List<UserPlantDto> =
            myPlantRepository.findNeedWaterPlantsByAlarmTimeInBatch(alarmTime)
        return ListItemReader(userPlantByAlarmTime)
    }

    @Bean
    @StepScope
    fun fertilizerNotificationItemReader(): ListItemReader<UserPlantDto> {
        val now: LocalTime = LocalTime.now()
        val alarmTime = AlarmTime.fromHour(now)
        val userPlantByAlarmTime: List<UserPlantDto> =
            myPlantRepository.findNeedFertilizerPlantsByAlarmTimeInBatch(alarmTime)
        return ListItemReader(userPlantByAlarmTime)
    }

    @Bean
    @StepScope
    fun healthCheckNotificationItemReader(): ListItemReader<UserPlantDto> {
        val now: LocalTime = LocalTime.now()
        val alarmTime = AlarmTime.fromHour(now)
        val userPlantByAlarmTime: List<UserPlantDto> =
            myPlantRepository.findNeedHealthCheckPlantsByAlarmTimeInBatch(alarmTime)
        return ListItemReader(userPlantByAlarmTime)
    }
}
