package dnd11th.blooming.core.repository.onboard

import dnd11th.blooming.core.entity.onboard.OnboardingResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OnboardResultRepository : JpaRepository<OnboardingResult, Long> {
    @Query("SELECT o FROM OnboardingResult o WHERE o.version = :version")
    fun findAllByVersion(
        @Param("version") version: Int,
    ): List<OnboardingResult>
}
