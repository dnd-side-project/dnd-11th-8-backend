package dnd11th.blooming.batch.weather

import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import dnd11th.blooming.redis.repository.weather.WeatherCareMessageRepository
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WeatherCareMessageWriter(
    private val weatherCareMessageRepository: WeatherCareMessageRepository
) {

    @Bean
    @StepScope
    fun weatherCareMessageItemWriter(): ItemWriter<WeatherCareMessage> {
        return ItemWriter { weatherMessageForRegions ->
            weatherMessageForRegions.forEach {
                weatherMessageForRegion -> weatherCareMessageRepository.save(weatherMessageForRegion)
            }
        }
    }
}
