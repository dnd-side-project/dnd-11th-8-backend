package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "Image Save Request",
    description = "이미지를 저장하는 요청",
)
data class ImageSaveRequest(
    @field:Schema(name = "이미지 URL", example = "image.com/17")
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
