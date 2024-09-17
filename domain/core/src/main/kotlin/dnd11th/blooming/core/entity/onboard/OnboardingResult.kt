package dnd11th.blooming.core.entity.onboard

import dnd11th.blooming.core.entity.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class OnboardingResult(
    @Column
    var version: Int,
    @Column
    var result: String,
    @Column
    var subTitle: String,
    @Column
    var illustUrl: String,
    @Column
    var description: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
