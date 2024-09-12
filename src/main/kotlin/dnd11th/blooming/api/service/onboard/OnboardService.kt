package dnd11th.blooming.api.service.onboard

import dnd11th.blooming.api.dto.onboard.OnboardResultRequest
import dnd11th.blooming.api.dto.onboard.OnboardResultResponse
import dnd11th.blooming.api.dto.onboard.OnboardScriptResponse
import dnd11th.blooming.domain.repository.onboard.OnboardAnswerRepository
import dnd11th.blooming.domain.repository.onboard.OnboardQuestionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnboardService(
    private val onboardQuestionRepository: OnboardQuestionRepository,
    private val onboardAnswerRepository: OnboardAnswerRepository,
) {
    @Transactional(readOnly = true)
    fun findScripts(): OnboardScriptResponse {
        val latestVersion = onboardQuestionRepository.findLatestVersion()

        val onboardingAnswers = onboardAnswerRepository.findAllByVersion(latestVersion)

        return OnboardScriptResponse.of(latestVersion, onboardingAnswers)
    }

    fun submitScripts(request: List<OnboardResultRequest>): OnboardResultResponse {
        TODO("Not yet implemented")
    }
}
