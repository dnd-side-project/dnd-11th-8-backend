package dnd11th.blooming.domain.entity.plant

enum class Toxicity(
    val displayName: String,
) {
    NOT_EXISTS("독성 없음"),
    EXISTS("독성 있음"),
}
