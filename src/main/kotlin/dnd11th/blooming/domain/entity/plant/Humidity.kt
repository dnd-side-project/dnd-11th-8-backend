package dnd11th.blooming.domain.entity.plant

enum class Humidity(
    val displayName: String,
    val humidityLevel: String,
    val apiName: String,
) {
    HUMIDITY_0_40(
        "40% 이하",
        "낮은 습도",
        "40% 이하",
    ),
    HUMIDITY_40_70(
        "40%~70%",
        "중간 습도",
        "40% ~ 70%",
    ),
    HUMIDITY_70_100(
        "70% 이상",
        "높은 습도",
        "70% 이상",
    ),
}
