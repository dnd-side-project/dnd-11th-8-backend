package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.location.LocationCreateDto
import dnd11th.blooming.domain.entity.user.User
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
        fun createLocation(dto: LocationCreateDto): Location =
            Location(
                name = dto.name,
            )

        fun createDefaultLocations(user: User): List<Location> =
            listOf(
                Location(name = "거실").also { it.user = user },
                Location(name = "침실").also { it.user = user },
                Location(name = "테라스").also { it.user = user },
            )
    }
}
