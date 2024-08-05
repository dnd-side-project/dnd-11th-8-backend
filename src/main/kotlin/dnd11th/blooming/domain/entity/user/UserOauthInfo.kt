package dnd11th.blooming.domain.entity.user

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
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User = user

    var email: String = email

    @Enumerated(EnumType.STRING)
    val provider: OauthProvider = provider
}
