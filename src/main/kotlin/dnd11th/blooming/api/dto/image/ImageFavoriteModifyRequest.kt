package dnd11th.blooming.api.dto.image

import jakarta.validation.constraints.NotNull

data class ImageFavoriteModifyRequest(
    @field:NotNull(message = "즐겨찾기 여부는 필수값입니다.")
    val favorite: Boolean,
)
