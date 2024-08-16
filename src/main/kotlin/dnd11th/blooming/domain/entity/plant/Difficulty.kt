package dnd11th.blooming.domain.entity.plant

enum class Difficulty(
    val displayName: String,
) {
    LOW("낮음 (잘 견딤)"),
    MEDIUM("보통 (약간 잘 견딤)"),
    HIGH("필요함"),
}
