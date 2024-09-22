package dnd11th.blooming.redis.repository.weather

import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WeatherCareMessageRepository: CrudRepository<WeatherCareMessage, String> {
}
