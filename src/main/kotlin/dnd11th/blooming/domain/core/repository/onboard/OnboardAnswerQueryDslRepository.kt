package dnd11th.blooming.domain.core.repository.onboard

import dnd11th.blooming.domain.core.entity.onboard.OnboardingAnswer

interface OnboardAnswerQueryDslRepository {
    fun findAllByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingAnswer>
}
