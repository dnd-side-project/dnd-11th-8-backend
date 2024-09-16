package dnd11th.blooming.domain.core.entity.plant

enum class Toxicity(
    val apiName: String,
) {
    NOT_EXISTS(
        "독성 없음",
    ),
    EXISTS(
        "독성 있음",
    ),
}
