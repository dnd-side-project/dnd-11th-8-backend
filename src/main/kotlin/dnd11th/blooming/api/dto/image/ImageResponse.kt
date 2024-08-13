package dnd11th.blooming.api.dto.image

import dnd11th.blooming.domain.entity.Image
import java.time.LocalDate

data class ImageResponse(
    val imageUrl: String,
    val favorite: Boolean,
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
