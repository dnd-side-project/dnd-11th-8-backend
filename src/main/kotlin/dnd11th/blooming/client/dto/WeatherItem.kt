package dnd11th.blooming.client.dto

import java.time.LocalDate

data class WeatherItem(
    val baseDate: LocalDate,
    val baseTime: String,
    val category: String,
    val fcstDate: LocalDate,
    val fcstTime: Int,
    val fcstValue: Int,
    val nx: Int,
    val ny: Int
)
/**
"baseDate": "20240807",
"baseTime": "0200",
"category": "REH",
"fcstDate": "20240807",
"fcstTime": "1000",
"fcstValue": "80",
"nx": 55,
"ny": 127
 **/
