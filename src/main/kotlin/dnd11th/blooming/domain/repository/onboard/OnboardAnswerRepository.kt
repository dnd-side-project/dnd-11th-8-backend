package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OnboardAnswerRepository : JpaRepository<OnboardingAnswer, Long> {
    @Query("SELECT oa FROM OnboardingAnswer oa JOIN FETCH oa.onboardingQuestion WHERE oa.onboardingQuestion.version = :version")
    fun findAllByVersion(
        @Param("version") version: Int,
    ): List<OnboardingAnswer>
}
