package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    name = "Image Response",
    description = "이미지 응답",
)
data class ImageResponse(
    @field:Schema(name = "이미지 URL", example = "image.com/17")
    val imageUrl: String,
    @field:Schema(name = "즐겨찾기 여부", example = "true")
    val favorite: Boolean,
    @field:Schema(name = "이미지를 저장한 날짜", example = "2000-05-17")
    val createdDate: LocalDate,
) {
    companion object {
        fun from(image: Image): ImageResponse =
            ImageResponse(
                imageUrl = image.url,
                favorite = image.favorite,
                createdDate = image.createdDate,
            )

        fun fromList(images: List<Image>): List<ImageResponse> =
            images.stream()
                .map { image -> from(image) }
                .toList()
    }
}
