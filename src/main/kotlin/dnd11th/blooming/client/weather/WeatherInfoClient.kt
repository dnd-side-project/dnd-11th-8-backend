package dnd11th.blooming.client.weather

import dnd11th.blooming.client.dto.WeatherItems
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "WeatherInfoClient",
    url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst",
)
interface WeatherInfoClient{

    @GetMapping("/")
    fun getWeatherInfo(@RequestParam serviceKey: String,
                       @RequestParam numberOfRows: Int,
                       @RequestParam pageNo: Int,
                       @RequestParam dataType: String = "JSON",
                       @RequestParam base_date: String,
                       @RequestParam base_time: String,
                       @RequestParam nx: Int,
                       @RequestParam ny: Int) : WeatherItems
}
