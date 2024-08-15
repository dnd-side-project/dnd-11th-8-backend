package dnd11th.blooming.api.dto.image

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(
    name = "Image Favorite Modify Request",
    description = "이미지 즐겨찾기 수정 요청",
)
data class ImageFavoriteModifyRequest(
    @field:Schema(description = "즐겨찾기 여부", example = "true")
    @field:NotNull(message = "즐겨찾기 여부는 필수값입니다.")
    val favorite: Boolean?,
)
