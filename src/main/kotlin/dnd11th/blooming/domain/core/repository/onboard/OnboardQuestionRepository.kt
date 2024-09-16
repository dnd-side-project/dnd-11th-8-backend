package dnd11th.blooming.domain.core.repository.onboard

import dnd11th.blooming.domain.core.entity.onboard.OnboardingQuestion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OnboardQuestionRepository : JpaRepository<OnboardingQuestion, Long> {
    @Query("SELECT MAX(oq.version) FROM OnboardingQuestion oq")
    fun findLatestVersion(): Int
}
