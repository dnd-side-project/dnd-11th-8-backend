package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class ImageSaveRequest(
    @field:NotBlank(message = "URL은 필수값입니다.")
    val imageUrl: String?,
) {
    fun toImage(
        myPlant: MyPlant,
        now: LocalDate,
    ): Image =
        Image(
            url = imageUrl!!,
            favorite = false,
            currentDate = now,
        ).also {
            it.setMyPlantRelation(myPlant)
        }
}
