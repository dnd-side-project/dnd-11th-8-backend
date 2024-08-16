package dnd11th.blooming.domain.entity.plant

enum class Water(
    val displayName: String,
) {
    WET("흙을 항상 축축하게 유지함"),
    MOIST("흙을 촉촉하게 유지함"),
    SURFACE_DRY("흙 표면이 말랐을 때 관수함"),
    DRY("흙 대부분이 말랐을 때 관수함"),
}
