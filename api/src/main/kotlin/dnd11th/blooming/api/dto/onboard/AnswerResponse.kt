package dnd11th.blooming.api.dto.onboard

import dnd11th.blooming.core.entity.onboard.OnboardingAnswer
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Answer Responses",
    description = "선택지 응답",
)
data class AnswerResponse(
    @field:Schema(description = "선택지 번호", example = "1")
    val answerNumber: Int,
    @field:Schema(description = "선택지 내용", example = "집에 누워있을래")
    val answer: String,
) {
    companion object {
        fun from(onboardingAnswers: List<OnboardingAnswer>): List<AnswerResponse> {
            return onboardingAnswers.map { onboardingAnswer ->
                AnswerResponse(
                    answerNumber = onboardingAnswer.answerNumber,
                    answer = onboardingAnswer.answer,
                )
            }
        }
    }
}
