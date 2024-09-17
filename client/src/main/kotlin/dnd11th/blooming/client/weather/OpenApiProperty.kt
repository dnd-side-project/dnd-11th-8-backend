package dnd11th.blooming.client.weather

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class OpenApiProperty(
    @Value("\${weather.serviceKey}")
    val serviceKey: String,
)
