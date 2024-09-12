package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface OnboardAnswerRepository : JpaRepository<OnboardingAnswer, Long>
