package dnd11th.blooming.batch

import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.repository.myplant.MyPlantRepository
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
        val alarmTime = AlarmTime.fromHour(now)

        val userPlantByAlarmTime: List<UserPlantDto> =
            myPlantRepository.findPlantsByAlarmTimeInBatch(
                alarmTime,
            )
        return ListItemReader(userPlantByAlarmTime)
    }
}
