package dnd11th.blooming.domain.entity.plant

enum class Water(
    private val description: String,
    val waterPerWeek: String,
    val periodDay: Int,
    val apiName: String,
) {
    WET(
        "흙을 항상 축축하게 유지",
        "일주일에 3번",
        2,
        "흙을 항상 축축하게 유지함(물에 잠김)",
    ),
    MOIST(
        "흙을 촉촉하게 유지",
        "일주일에 2번",
        3,
        "흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)",
    ),
    SURFACE_DRY(
        "흙 표면이 말랐을 때 충분히 관수",
        "일주일에 1번",
        5,
        "토양 표면이 말랐을때 충분히 관수함",
    ),
    DRY(
        "흙 대부분이 말랐을 때 충분히 관수",
        "일주일에 1번",
        7,
        "화분 흙 대부분이 말랐을때 충분히 관수함",
    ),
    ;

    fun getWrittenDecription(): String {
        return "${description}할 것"
    }

    fun getColloquialDecription(): String {
        return "${description}해주세요."
    }
}
