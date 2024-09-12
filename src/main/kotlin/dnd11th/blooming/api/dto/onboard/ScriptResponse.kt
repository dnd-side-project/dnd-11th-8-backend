package dnd11th.blooming.api.dto.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer
import dnd11th.blooming.domain.entity.onboard.OnboardingQuestion
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
) {
    companion object {
        fun from(onboardingAnswers: List<OnboardingAnswer>): List<ScriptResponse> {
            val onboardingQuestions: List<OnboardingQuestion> =
                onboardingAnswers
                    .mapNotNull { answer -> answer.onboardingQuestion }
                    .distinctBy { oq -> oq.questionNumber }

            return onboardingQuestions.map { oq ->
                ScriptResponse(
                    questionNumber = oq.questionNumber,
                    question = oq.question,
                    answers = ScriptAnswerResponse.from(onboardingAnswers.filter { oa -> oa.onboardingQuestion == oq }),
                )
            }
        }
    }
}
