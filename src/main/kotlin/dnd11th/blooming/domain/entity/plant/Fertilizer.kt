package dnd11th.blooming.domain.entity.plant

enum class Fertilizer(
    val periodWeek: Int,
    val apiName: String,
) {
    NOT_VERY_DEMANDING(
        8,
        "비료를 거의 요구하지 않음",
    ),
    DEMANDING(
        4,
        "비료를 보통 요구함",
    ),
}
