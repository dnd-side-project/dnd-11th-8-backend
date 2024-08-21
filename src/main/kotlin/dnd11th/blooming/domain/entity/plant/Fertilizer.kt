package dnd11th.blooming.domain.entity.plant

enum class Fertilizer(
    val periodWeek: Int,
    val apiName: String,
) {
    NOT_DEMAND(
        12,
        "비료를 요구하지 않음",
    ),
    LOW_DEMAND(
        8,
        "비료를 거의 요구하지 않음",
    ),
    DEMAND(
        4,
        "비료를 보통 요구함",
    ),
    HIGH_DEMAND(
        4,
        "비료를 많이 요구함",
    ),
}
