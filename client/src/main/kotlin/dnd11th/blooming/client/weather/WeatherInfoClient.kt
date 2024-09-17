package dnd11th.blooming.client.weather

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "WeatherInfoClient",
    url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst",
)
interface WeatherInfoClient {
    companion object {
        const val FIXED_PAGE_NUMBER = 1
        const val FIXED_NUMBER_OF_ROWS = 288
        const val FIXED_DATA_TYPE = "JSON"
        const val FIXED_BASE_TIME = "0200"
    }

    @GetMapping
    fun getWeatherInfo(
        @RequestParam serviceKey: String,
        @RequestParam pageNo: Int = FIXED_PAGE_NUMBER,
        @RequestParam numOfRows: Int = FIXED_NUMBER_OF_ROWS,
        @RequestParam dataType: String = FIXED_DATA_TYPE,
        @RequestParam base_date: String,
        @RequestParam base_time: String = FIXED_BASE_TIME,
        @RequestParam nx: Int,
        @RequestParam ny: Int,
    ): WeatherResponse
}
