package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer

interface OnboardAnswerQueryDslRepository {
    fun findAllByQuestionNumberAndAnswerNumberIn(resultPairs: List<Pair<Int, Int>>): List<OnboardingAnswer>
}
