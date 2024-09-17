package dnd11th.blooming.core.repository.onboard

import dnd11th.blooming.core.entity.onboard.OnboardingAnswer

interface OnboardAnswerQueryDslRepository {
    fun findAllByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingAnswer>
}
