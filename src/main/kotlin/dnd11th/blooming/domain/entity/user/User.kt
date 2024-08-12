package dnd11th.blooming.domain.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    val email: String,
    val nickname: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val nx: Int = 0

    val ny: Int = 0

    companion object {
        fun create(claims: UserClaims): User {
            return User(claims.email)
        }
    }
}
