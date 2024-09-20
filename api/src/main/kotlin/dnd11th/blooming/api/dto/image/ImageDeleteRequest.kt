package dnd11th.blooming.api.dto.image

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema(
    name = "Image Delete Request",
    description = "이미지 삭제 요청",
)
data class ImageDeleteRequest(
    @field:Schema(description = "삭제할 이미지 ID", example = "[1, 2, 3]")
    @field:NotNull(message = "삭제할 이미지 ID는 필수입니다.")
    val imageIds: List<Long>?,
)
