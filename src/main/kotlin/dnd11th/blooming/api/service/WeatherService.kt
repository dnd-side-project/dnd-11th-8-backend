package dnd11th.blooming.api.service

import dnd11th.blooming.client.dto.WeatherItems
import dnd11th.blooming.client.weather.WeatherInfoClient
import dnd11th.blooming.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WeatherService(
    private val weatherInfoClient: WeatherInfoClient,
    private val userRepository: UserRepository
) {

    fun createPlantMessageByWeather() {
        var nx: Int = 55
        var ny: Int = 127
        //TODO 어느 시간대 어느 페이지 호추 할 것인지 DSL
        val weatherItems: WeatherItems = weatherInfoClient.getWeatherInfo()
        //TODO ITEM들을 MESSAGE로 변환하는 DSL
    }
}
