package dnd11th.blooming.api.dto.myplant

import dnd11th.blooming.domain.core.entity.myplant.MyPlant

data class MyPlantWithImageUrl(
    val myPlant: MyPlant,
    val imageUrl: String?,
)
