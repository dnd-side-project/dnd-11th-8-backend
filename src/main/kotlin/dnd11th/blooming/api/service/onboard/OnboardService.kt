package dnd11th.blooming.api.service.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.OnboardScriptResponse
import dnd11th.blooming.domain.entity.onboard.OnboardingResult
import dnd11th.blooming.domain.repository.onboard.OnboardAnswerRepository
import dnd11th.blooming.domain.repository.onboard.OnboardQuestionRepository
import dnd11th.blooming.domain.repository.onboard.OnboardResultRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnboardService(
    private val onboardQuestionRepository: OnboardQuestionRepository,
    private val onboardAnswerRepository: OnboardAnswerRepository,
    private val onboardResultRepository: OnboardResultRepository,
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
        val resultSet = onboardResultRepository.findAllByVersion(version)

        val results =
            onboardAnswerRepository.findAllOnboardingResultByQuestionNumberAndAnswerNumberIn(
                request.map { Pair(it.questionNumber, it.answerNumber) },
            )

        val result = OnboardingResult.calculateResult(resultSet, results)

        return OnboardResultResponse.from(result)
    }
}
