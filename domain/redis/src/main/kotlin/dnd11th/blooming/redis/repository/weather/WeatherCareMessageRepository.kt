package dnd11th.blooming.redis.repository.weather

import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import org.springframework.stereotype.Repository

@Repository
interface WeatherCareMessageRepository {
    fun save(weatherCareMessage: WeatherCareMessage)

    fun saveAll(weatherCareMessages: List<WeatherCareMessage>)

    fun findByRegion(regionKey: String): WeatherCareMessage?
}
