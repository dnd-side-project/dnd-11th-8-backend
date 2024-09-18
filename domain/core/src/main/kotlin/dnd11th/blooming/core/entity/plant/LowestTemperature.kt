package dnd11th.blooming.core.entity.plant

enum class LowestTemperature(
    val temperature: Int,
    val apiName: String,
) {
    LOWEST_TEMPERATURE_0(
        0,
        "0도 이하",
    ),
    LOWEST_TEMPERATURE_5(
        5,
        "5도",
    ),
    LOWEST_TEMPERATURE_7(
        7,
        "7도",
    ),
    LOWEST_TEMPERATURE_10(
        10,
        "10도",
    ),
    LOWEST_TEMPERATURE_13(
        13,
        "13도 이상",
    ),
}
