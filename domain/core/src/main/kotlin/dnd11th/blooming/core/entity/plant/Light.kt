package dnd11th.blooming.core.entity.plant

enum class Light(
    val apiName: String,
) {
    LOW(
        "낮은 광도(300~800Lux)",
    ),
    MEDIUM(
        "중간 광도(800~1,500Lux)",
    ),
    HIGH(
        "높은 광도(1,500~10,000Lux)",
    ),
    LOW_MEDIUM(
        "낮은 광도(300~800Lux), 중간 광도(800~1,500Lux)",
    ),
    MEDIUM_HIGH(
        "중간 광도(800~1,500Lux), 높은 광도(1,500~10,000Lux)",
    ),
    LOW_MEDIUM_HIGH(
        "낮은 광도(300~800Lux), 중간 광도(800~1,500Lux), 높은 광도(1,500~10,000Lux)",
    ),
}
