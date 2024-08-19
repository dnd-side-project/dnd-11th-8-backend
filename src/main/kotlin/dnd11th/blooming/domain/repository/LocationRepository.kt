package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long> {
    fun findByName(name: String): Location?

    fun findAllByUser(user: User): List<Location>

    fun findByIdAndUser(
        id: Long,
        user: User,
    ): Location?
}
