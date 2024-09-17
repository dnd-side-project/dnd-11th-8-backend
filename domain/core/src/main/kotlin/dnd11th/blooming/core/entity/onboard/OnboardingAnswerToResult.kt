package dnd11th.blooming.core.entity.onboard

import dnd11th.blooming.core.entity.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class OnboardingAnswerToResult : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "answer_id")
    var onboardingAnswer: OnboardingAnswer? = null

    @ManyToOne
    @JoinColumn(name = "result_id")
    var onboardingResult: OnboardingResult? = null
}
