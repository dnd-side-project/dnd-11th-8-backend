package dnd11th.blooming.domain.repository.onboard

import dnd11th.blooming.domain.entity.onboard.OnboardingResult
import org.springframework.data.jpa.repository.JpaRepository

interface OnboardResultRepository : JpaRepository<OnboardingResult, Long>
