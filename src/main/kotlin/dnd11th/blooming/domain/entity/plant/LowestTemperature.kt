package dnd11th.blooming.domain.entity.plant

enum class LowestTemperature(
    val displayName: String,
    val apiName: String,
) {
    LOWEST_TEMPERATURE_0("0도", "0도 이하"),
    LOWEST_TEMPERATURE_5("5도", "5도"),
    LOWEST_TEMPERATURE_7("7도", "7도"),
    LOWEST_TEMPERATURE_10("10도", "10도"),
    LOWEST_TEMPERATURE_13("13도", "13도 이상"),
}
