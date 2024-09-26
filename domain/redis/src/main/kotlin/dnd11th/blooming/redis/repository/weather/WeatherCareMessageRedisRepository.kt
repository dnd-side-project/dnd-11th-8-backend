package dnd11th.blooming.redis.repository.weather

import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class WeatherCareMessageRedisRepository(
    private val redisTemplate: RedisTemplate<String, WeatherCareMessage>,
) : WeatherCareMessageRepository {
    private val hashOps = redisTemplate.opsForHash<String, WeatherCareMessage>()

    override fun save(weatherCareMessage: WeatherCareMessage) {
        hashOps.put(getKey(weatherCareMessage.regionKey), weatherCareMessage.regionKey, weatherCareMessage)
    }

    override fun saveAll(weatherCareMessages: List<WeatherCareMessage>) {
        redisTemplate.executePipelined {
            weatherCareMessages.forEach { message ->
                hashOps.put(getKey(message.regionKey), message.regionKey, message)
            }
            null
        }
    }

    override fun findByRegion(regionKey: String): WeatherCareMessage? {
        return hashOps.get(getKey(regionKey), regionKey)
    }

    private fun getKey(regionKey: String) = "weatherCareMessage:$regionKey"
}
