package dnd11th.blooming.domain.core.entity.plant

enum class Difficulty(
    val targetPeople: String,
    val apiName: String,
) {
    LOW(
        "초보식집사",
        "낮음 (잘 견딤)",
    ),
    MEDIUM(
        "경험자",
        "보통 (약간 잘 견딤)",
    ),
    HIGH(
        "고수식집사",
        "필요함",
    ),
    VERY_HIGH(
        "고수식집사",
        "특별 관리 요구",
    ),
}
