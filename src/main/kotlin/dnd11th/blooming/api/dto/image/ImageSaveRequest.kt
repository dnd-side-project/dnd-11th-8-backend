package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import java.time.LocalDate

data class ImageSaveRequest(
    val imageUrl: String,
) {
    fun toImage(
        myPlant: MyPlant,
        now: LocalDate,
    ): Image =
        Image(
            url = imageUrl,
            favorite = false,
            currentDate = now,
        ).also {
            it.setMyPlantRelation(myPlant)
        }
}
