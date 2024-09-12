package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingResult

interface OnboardAnswerQueryDslRepository {
    fun findAllOnboardingResultByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingResult>
}
