package dnd11th.blooming.core.repository.onboard

import dnd11th.blooming.core.entity.onboard.OnboardingAnswer
import dnd11th.blooming.core.entity.onboard.OnboardingAnswerToResult
import dnd11th.blooming.core.entity.onboard.OnboardingResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OnboardAnswerToResultRepository : JpaRepository<OnboardingAnswerToResult, Long> {
    @Query(
        """
        SELECT oar.onboardingResult, COUNT(oar) as resultCount
        FROM OnboardingAnswerToResult oar
        WHERE oar.onboardingAnswer IN :answers
        GROUP BY oar.onboardingResult
        ORDER BY resultCount DESC
        LIMIT 1
    """,
    )
    fun findMostSelectedResult(
        @Param("answers") answers: List<OnboardingAnswer>,
    ): OnboardingResult
}
