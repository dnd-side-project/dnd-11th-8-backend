package dnd11th.blooming.domain.entity.plant

enum class Water(
    val periodDay: Int,
    val waterPerWeek: Int,
    val description: String,
    val apiName: String,
) {
    WET(
        2,
        3,
        "흙을 항상 축축하게 유지",
        "흙을 항상 축축하게 유지함(물에 잠김)",
    ),
    MOIST(
        3,
        2,
        "흙을 촉촉하게 유지",
        "흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)",
    ),
    SURFACE_DRY(
        5,
        1,
        "흙 표면이 말랐을 때 충분히 관수",
        "토양 표면이 말랐을때 충분히 관수함",
    ),
    DRY(
        7,
        1,
        "흙 대부분이 말랐을 때 충분히 관수",
        "화분 흙 대부분이 말랐을때 충분히 관수함",
    ),
}
