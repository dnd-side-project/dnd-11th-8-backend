package dnd11th.blooming.redis.entity.weather

import java.io.Serializable

data class WeatherCareMessage(
    val regionKey: String,
    val messages: List<Message>,
) : Serializable {
    companion object {
        fun create(
            nx: Int,
            ny: Int,
            types: List<WeatherCareMessageType>,
        ): WeatherCareMessage {
            val regionKey = "nx${nx}ny$ny"
            val messages =
                types.map { type ->
                    Message(type.name, type.title, type.message)
                }
            return WeatherCareMessage(regionKey, messages)
        }
    }
}

data class Message(
    val status: String,
    val title: String,
    val message: String,
): Serializable
