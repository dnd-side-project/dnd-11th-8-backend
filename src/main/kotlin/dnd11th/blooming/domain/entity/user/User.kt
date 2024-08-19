package dnd11th.blooming.domain.entity.user

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column
    val email: String,
    nickname: String,
    alarmTime: AlarmTime,
    nx: Int,
    ny: Int,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : BaseEntity() {
    @Column
    var nickname: String = nickname
        protected set

    @Column
    var alarmStatus: Boolean = true
        protected set

    @Column
    @Enumerated(EnumType.STRING)
    var alarmTime: AlarmTime = alarmTime
        protected set

    @Column
    var nx: Int = nx
        protected set

    @Column
    var ny: Int = ny
        protected set

    companion object {
        fun create(
            email: String,
            nickname: String,
            alarmTime: AlarmTime,
            nx: Int,
            ny: Int,
        ): User {
            return User(email, nickname, alarmTime, nx, ny)
        }
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }
}
