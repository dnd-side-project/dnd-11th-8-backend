package dnd11th.blooming.redis.entity.weather

import dnd11th.blooming.redis.entity.WeatherCareMessageType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("weatherCareMessage")
data class WeatherCareMessage(
    @Id
    val regionId: Int,
    val messages: List<Message>,
){
    companion object {
        fun create(regionId: Int, types: List<WeatherCareMessageType>): WeatherCareMessage {
            val messages = types.map { type ->
                Message(type.title, type.message)
            }
            return WeatherCareMessage(regionId, messages)
        }
    }
}

data class Message(
    val title: String,
    val message: String
)
