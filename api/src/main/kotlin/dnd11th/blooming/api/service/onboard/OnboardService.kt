package dnd11th.blooming.api.service.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.OnboardScriptResponse
import dnd11th.blooming.core.entity.onboard.OnboardingAnswer
import dnd11th.blooming.core.repository.onboard.OnboardAnswerRepository
import dnd11th.blooming.core.repository.onboard.OnboardAnswerToResultRepository
import dnd11th.blooming.core.repository.onboard.OnboardQuestionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnboardService(
    private val onboardQuestionRepository: OnboardQuestionRepository,
    private val onboardAnswerToResultRepository: OnboardAnswerToResultRepository,
    private val onboardAnswerRepository: OnboardAnswerRepository,
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
