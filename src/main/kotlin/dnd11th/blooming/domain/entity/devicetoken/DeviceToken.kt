package dnd11th.blooming.domain.entity.devicetoken

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "device_token")
class DeviceToken(
    @Column
    val token: String,
    @Column
    val userId: Long,
    @Column
    var active: Boolean = true,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : BaseEntity() {
    fun invalidToken() {
        this.active = false
    }
}
