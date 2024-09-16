package dnd11th.blooming.domain.core.entity.user

import dnd11th.blooming.domain.core.entity.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class UserOauthInfo(
    user: User,
    email: String,
    provider: OauthProvider,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User = user

    @Column
    var email: String = email

    @Enumerated(EnumType.STRING)
    val provider: OauthProvider = provider
}
