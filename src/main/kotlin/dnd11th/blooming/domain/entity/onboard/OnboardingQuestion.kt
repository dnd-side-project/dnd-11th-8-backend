package dnd11th.blooming.domain.entity.onboard

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class OnboardingQuestion(
    @Column
    var version: Int,
    @Column
    var questionNumber: Int,
    @Column
    var question: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
