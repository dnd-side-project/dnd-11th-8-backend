package dnd11th.blooming.batch.weather

import dnd11th.blooming.client.weather.OpenApiProperty
import dnd11th.blooming.client.weather.WeatherInfoClient
import dnd11th.blooming.client.weather.WeatherItem
import dnd11th.blooming.common.util.Logger.Companion.log
import dnd11th.blooming.core.entity.region.Region
import dnd11th.blooming.redis.entity.WeatherCareMessageType
import dnd11th.blooming.redis.entity.weather.WeatherCareMessage
import dnd11th.blooming.redis.repository.weather.WeatherCareMessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Configuration
class WeatherCareMessageWriter(
    private val weatherCareMessageRepository: WeatherCareMessageRepository,
    private val weatherInfoClient: WeatherInfoClient,
    private val openApiProperty: OpenApiProperty,
) {
    companion object {
        private const val HUMIDITY_KEY = "REH"
        private const val TEMPERATURE_KEY = "TMP"
        private const val MAX_HUMIDITY = 100
        private const val MIN_HUMIDITY = 0
        private const val MAX_TEMPERATURE = 40
        private const val MIN_TEMPERATURE = -20
        private const val HIGH_HUMIDITY_THRESHOLD = 80
        private const val LOW_HUMIDITY_THRESHOLD = 20
        private const val LOW_TEMPERATURE_THRESHOLD = 5
        private const val HIGH_TEMPERATURE_THRESHOLD = 30
    }

    @Bean
    @StepScope
    fun weatherCareMessageItemWriter(): ItemWriter<Region> {
        return ItemWriter { regions ->
            val now: LocalDate = LocalDate.now()
            runBlocking {
                val weatherCareMessages: List<WeatherCareMessage?> = regions.map { region ->
                    async(Dispatchers.IO) {
                        try {
                            val weatherItems: List<WeatherItem> =
                                weatherInfoClient.getWeatherInfo(
                                    serviceKey = openApiProperty.serviceKey,
                                    base_date = formatDate(now),
                                    nx = region.nx,
                                    ny = region.ny
                                ).toWeatherItems()
                            val weatherCareMessageTypes: List<WeatherCareMessageType> = determineWeatherMessages(weatherItems)
                            WeatherCareMessage.create(region.id, weatherCareMessageTypes)
                        } catch (e: Exception) {
                            log.error(e) { "Failed to fetch weather data for region: ${region.id}" }
                            null
                        }
                    }
                }.awaitAll()
                println(weatherCareMessages.size)
            }
        }
    }

    private fun determineWeatherMessages(items: List<WeatherItem>): List<WeatherCareMessageType> {
        var maxHumidity = MIN_HUMIDITY
        var minHumidity = MAX_HUMIDITY
        var maxTemperature = MIN_TEMPERATURE
        var minTemperature = MAX_TEMPERATURE

        items.forEach { item ->
            when (item.category) {
                HUMIDITY_KEY -> {
                    val humidity = item.fcstValue.toInt()
                    maxHumidity = maxOf(maxHumidity, humidity)
                    minHumidity = minOf(minHumidity, humidity)
                }
                TEMPERATURE_KEY -> {
                    val temperature = item.fcstValue.toInt()
                    maxTemperature = maxOf(maxTemperature, temperature)
                    minTemperature = minOf(minTemperature, temperature)
                }
            }
        }

        return mutableListOf<WeatherCareMessageType>().apply {
            if (maxHumidity >= HIGH_HUMIDITY_THRESHOLD) add(WeatherCareMessageType.HUMIDITY)
            if (minHumidity <= LOW_HUMIDITY_THRESHOLD) add(WeatherCareMessageType.DRY)
            if (minTemperature <= LOW_TEMPERATURE_THRESHOLD) add(WeatherCareMessageType.COLD)
            if (maxTemperature >= HIGH_TEMPERATURE_THRESHOLD) add(WeatherCareMessageType.HOT)
        }
    }

    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return date.format(formatter)
    }
}
