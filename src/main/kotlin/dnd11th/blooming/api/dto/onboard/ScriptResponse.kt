package dnd11th.blooming.api.dto.onboard

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Script Responses",
    description = "질문지 응답",
)
data class ScriptResponse(
    @field:Schema(description = "질문 번호", example = "1")
    val questionNumber: Int,
    @field:Schema(description = "질문 내용", example = "휴일에 내가 하고 싶은 것은?")
    val question: String,
    @field:Schema(description = "선택지 문항")
    val answers: List<ScriptAnswerResponse>,
)
