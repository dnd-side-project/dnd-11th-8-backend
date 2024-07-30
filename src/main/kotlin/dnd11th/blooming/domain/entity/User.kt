package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    email: String,
    nickname: String,
) {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val email: String = email

    var nickname: String = nickname

    constructor(id: Long, email: String, nickname: String) : this(email, nickname) {
        this.id = id
    }

    companion object {
        fun create(claims: UserClaims): User {
            return User(claims.email, claims.nickname)
        }

        fun createWithId(
            id: Long,
            email: String,
            nickname: String,
        ): User {
            return User(id, email, nickname)
        }
    }
}
