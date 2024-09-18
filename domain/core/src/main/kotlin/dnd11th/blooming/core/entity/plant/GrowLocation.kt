package dnd11th.blooming.core.entity.plant

enum class GrowLocation(
    val description: String,
) {
    ROOM_DARK(
        "실내 어두운 곳, 거실 내측, 거실 창측, 베란다 내측, 베란다 창측",
    ),
    ROOM(
        "거실 내측, 거실 창측, 베란다 내측, 베란다 창측",
    ),
    ROOM_WINDOW(
        "거실 창측, 베란다 내측, 베란다 창측",
    ),
    VERANDA(
        "베란다 내측, 베란다 창측",
    ),
    VERANDA_WINDOW(
        "베란다 창측",
    ),
}
