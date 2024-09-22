package dnd11th.blooming.api.service.weather

import dnd11th.blooming.client.weather.OpenApiProperty
import dnd11th.blooming.client.weather.WeatherInfoClient
import dnd11th.blooming.client.weather.WeatherItem
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.core.entity.user.User
import dnd11th.blooming.core.repository.user.UserRepository
import dnd11th.blooming.redis.entity.WeatherCareMessageType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
@Transactional(readOnly = true)
class WeatherService(
    private val openApiProperty: OpenApiProperty,
    private val weatherInfoClient: WeatherInfoClient,
    private val userRepository: UserRepository,
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

    fun createWeatherMessage(
        loginUser: User,
        now: LocalDateTime,
    ): List<WeatherCareMessageType> {
        val user: User =
            userRepository.findById(loginUser.id!!).orElseThrow {
                throw NotFoundException(ErrorType.USER_NOT_FOUND)
            }

        val weatherItems: List<WeatherItem> =
            weatherInfoClient.getWeatherInfo(
                serviceKey = openApiProperty.serviceKey,
                base_date = getBaseDate(now),
                nx = user.nx,
                ny = user.ny,
            ).toWeatherItems()

        return determineWeatherMessages(weatherItems)
    }

    /**
     * 과습 -> 그날 습도가 80 이상 넘어가는 날 있으면 HUMIDITY
     * 건조 -> 그날 습도가 20 이하 내려가는 시간 있으면 DRY
     * 한파 -> 최저 온도가 5도 이하로 내려가면 COLD
     * 더위 -> 최고 온도가 30도 이상 올라가는 날 있으면 HOT
     */
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

    /**
     * 현재 시간이 2시 10분을 지났다면, 현재 날짜를 반환합니다.
     * 그렇지 않다면, 어제 날짜를 반환합니다.
     */
    private fun getBaseDate(now: LocalDateTime): String {
        val baseTime = LocalTime.of(2, 20)
        return if (now.toLocalTime().isAfter(baseTime)) {
            formatDate(now.toLocalDate())
        } else {
            formatDate(now.toLocalDate().minusDays(1))
        }
    }

    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return date.format(formatter)
    }
}
