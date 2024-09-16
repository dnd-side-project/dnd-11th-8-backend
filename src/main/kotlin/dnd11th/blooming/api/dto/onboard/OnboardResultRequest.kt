package dnd11th.blooming.api.dto.onboard

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Onboard Result Request",
    description = "온보딩 결과 요청",
)
data class OnboardResultRequest(
    @field:Schema(description = "질문 번호", example = "1")
    val questionNumber: Int,
    @field:Schema(description = "응답 번호", example = "2")
    val answerNumber: Int,
)
