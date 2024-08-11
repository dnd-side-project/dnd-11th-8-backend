package dnd11th.blooming.domain.entity.user

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(
    email: String,
    nickname: String,
    currentDate: LocalDate = LocalDate.now(),
) : BaseEntity(currentDate) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val email: String = email

    val nickname: String = nickname

    val nx: Int = 0

    val ny: Int = 0

    companion object {
        fun create(claims: UserClaims): User {
            return User(claims.email, claims.nickname)
        }
    }
}
