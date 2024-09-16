package dnd11th.blooming.api.service.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.OnboardScriptResponse
import dnd11th.blooming.domain.core.entity.onboard.OnboardingAnswer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnboardService(
    private val onboardQuestionRepository: dnd11th.blooming.domain.core.repository.onboard.OnboardQuestionRepository,
    private val onboardAnswerToResultRepository: dnd11th.blooming.domain.core.repository.onboard.OnboardAnswerToResultRepository,
    private val onboardAnswerRepository: dnd11th.blooming.domain.core.repository.onboard.OnboardAnswerRepository,
) {
    @Transactional(readOnly = true)
    fun findScripts(): OnboardScriptResponse {
        val latestVersion = onboardQuestionRepository.findLatestVersion()

        val onboardingAnswers = onboardAnswerRepository.findAllByVersion(latestVersion)

        return OnboardScriptResponse.of(latestVersion, onboardingAnswers)
    }

    @Transactional(readOnly = true)
    fun submitScripts(
        version: Int,
        request: List<OnboardResultRequest>,
    ): OnboardResultResponse {
        val selectedAnswers: List<OnboardingAnswer> =
            onboardAnswerRepository.findAllByQuestionNumberAndAnswerNumberIn(
                request.map { Pair(it.questionNumber, it.answerNumber) },
            )

        val selectedResult = onboardAnswerToResultRepository.findMostSelectedResult(selectedAnswers)

        return OnboardResultResponse.from(selectedResult)
    }
}
