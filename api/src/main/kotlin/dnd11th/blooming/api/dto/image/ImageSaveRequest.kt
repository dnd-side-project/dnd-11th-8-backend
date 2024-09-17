package dnd11th.blooming.api.dto.image

import dnd11th.blooming.core.entity.image.ImageCreateDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(
    name = "Image Save Request",
    description = "이미지를 저장하는 요청",
)
data class ImageSaveRequest(
    @field:Schema(description = "이미지 URL", example = "image.com/17")
    @field:NotBlank(message = "URL은 필수값입니다.")
    val imageUrl: String?,
) {
    fun toImageCreateDto(): ImageCreateDto =
        ImageCreateDto(
            url = imageUrl!!,
        )
}
