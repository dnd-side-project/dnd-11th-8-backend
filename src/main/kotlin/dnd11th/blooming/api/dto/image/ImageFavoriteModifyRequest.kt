package dnd11th.blooming.api.dto.image

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Image Favorite Modify Request",
    description = "이미지 즐겨찾기 수정 요청",
)
data class ImageFavoriteModifyRequest(
    @field:Schema(description = "즐겨찾기 여부", example = "true")
    val favorite: Boolean,
)
