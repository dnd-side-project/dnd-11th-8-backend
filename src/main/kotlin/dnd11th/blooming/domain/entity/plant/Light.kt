package dnd11th.blooming.domain.entity.plant

enum class Light(
    val displayName: String,
) {
    LOW("낮은 광도(300~800Lux)"),
    MEDIUM("중간 광도(800~1,500Lux)"),
    HIGH("높은 광도(1,500~10,000Lux)"),
}
