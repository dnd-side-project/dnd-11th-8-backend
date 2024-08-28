package dnd11th.blooming.batch

import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PlantNotificationReader(
    private val entityManagerFactory: EntityManagerFactory,
) {
    @StepScope
    @Bean
    fun waterNotificationItemReader(
        @Value("#{jobParameters['alarmTime']}") alarmTime: String,
    ): ItemReader<UserPlantDto> {
        val query: String =
            "SELECT new dnd11th.blooming.batch.UserPlantDTO" +
                "(u.id, u.email, mp.id, mp.nickname, mp.lastWateredDate, mp.waterPeriod) " +
                "FROM User u JOIN u.myPlants mp " +
                "WHERE u.alarmStatus = true " +
                "AND u.alarmTime = $alarmTime " +
                "AND mp.waterAlarm = true " +
                "AND DATEDIFF(CURRENT_DATE, mp.lastWateredDate) = mp.waterPeriod"
        return JpaPagingItemReaderBuilder<UserPlantDto>()
            .name("waterNotificationItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(query)
            .pageSize(100)
            .build()
    }
}
