package dnd11th.blooming.api.dto.image

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class ImageFavoriteModifyRequest(
    @field:NotNull(message = "즐겨찾기 여부는 필수값입니다.")
    @JsonProperty("favorite")
    private val _favorite: Boolean?,
) {
    val favorite: Boolean
        get() = _favorite!!
}
