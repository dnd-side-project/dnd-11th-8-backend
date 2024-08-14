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
import java.time.LocalDate

@Entity
class Location(
    @Column
    var name: String,
    currentDate: LocalDate = LocalDate.now(),
) : BaseEntity(currentDate) {
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
            now: LocalDate,
        ): Location =
            Location(
                name = dto.name,
                currentDate = now,
            )
    }
}
