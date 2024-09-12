package dnd11th.blooming.api.dto.onboard

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Script Answer Responses",
    description = "선택지 응답",
)
data class ScriptAnswerResponse(
    @field:Schema(description = "선택지 번호", example = "1")
    val answerNumber: Int,
    @field:Schema(description = "선택지 내용", example = "집에 누워있을래")
    val answer: String,
)
