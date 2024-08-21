package dnd11th.blooming.domain.entity.plant

enum class GrowTemperature(
    val lowTemperature: Int,
    val highTemperature: Int,
) {
    GROW_TEMPERATURE_16_20(
        16,
        20,
    ),
    GROW_TEMPERATURE_21_25(
        21,
        25,
    ),
}
