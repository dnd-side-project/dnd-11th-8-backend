package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface OnboardQuestionRepository : JpaRepository<OnboardingQuestion, Long>
