package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import java.time.LocalDate

data class ImageSaveRequest(
    val imageUrl: String,
) {
    fun toImage(now: LocalDate): Image =
        Image(
            url = imageUrl,
            favorite = false,
            currentDate = now,
        )
}
