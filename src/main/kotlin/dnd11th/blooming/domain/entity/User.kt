package dnd11th.blooming.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
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
    val id: Long? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val locations: MutableList<Location> = mutableListOf()

    val email: String = email

    var nickname: String = nickname

    companion object {
        fun create(claims: UserClaims): User {
            return User(claims.email, claims.nickname)
        }
    }
}
