package dnd11th.blooming.api.dto.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Onboard Script Response",
    description = "온보딩 질문지 응답",
)
data class OnboardScriptResponse(
    @field:Schema(description = "온보딩 버전", example = "1")
    val version: Int,
    @field:Schema(description = "질문지")
    val questions: List<QuestionResponse>,
) {
    companion object {
        fun of(
            version: Int,
            onboardingAnswers: List<OnboardingAnswer>,
        ): OnboardScriptResponse =
            OnboardScriptResponse(
                version = version,
                questions = QuestionResponse.from(onboardingAnswers),
            )
    }
}
