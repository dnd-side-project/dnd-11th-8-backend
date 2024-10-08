package dnd11th.blooming.core.entity.location

import dnd11th.blooming.core.entity.common.BaseEntity
import dnd11th.blooming.core.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Location(
    @Column
    var name: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    fun modifyName(name: String) {
        this.name = name
    }

    companion object {
        fun createLocation(
            dto: LocationCreateDto,
            user: User,
        ): Location =
            Location(
                name = dto.name,
            ).also {
                it.user = user
            }
    }
}
