package dnd11th.blooming.client.weather

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.ExternalServerException

data class WeatherResponse(
    val response: ResponseData,
) {
    fun toWeatherItems(): List<WeatherItem> {
        validate()
        return response.body?.items?.item ?: emptyList()
    }

    private fun validate() {
        if (response.header.resultCode != "00") {
            throw ExternalServerException(ErrorType.OPEN_API_CALL_EXCEPTION)
        }
    }
}

data class ResponseData(
    val header: HeaderData,
    val body: BodyData?,
)

data class HeaderData(
    val resultCode: String,
    val resultMsg: String,
)

data class BodyData(
    val items: Items,
)

data class Items(
    val item: List<WeatherItem>,
)

data class WeatherItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
    val nx: Int,
    val ny: Int,
)
