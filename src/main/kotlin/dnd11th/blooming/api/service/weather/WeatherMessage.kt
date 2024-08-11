package dnd11th.blooming.api.service.weather

enum class WeatherMessage(val title: String, val message: List<String>) {
    HUMIDITY("과습주의보", mutableListOf("과습이에요", "과습입니다.")),
    DRY("건조주의보", mutableListOf("건조에요", "건조입니다")),
    COLD("한파주의보", mutableListOf("한파입니다", "한파에요")),
    HOT("더위주의보", mutableListOf("덥습니다", "더워요")),
}
