package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LocationRepository : JpaRepository<Location, Long> {
    fun findAllByUser(user: User): List<Location>

    fun findByIdAndUser(
        id: Long,
        user: User,
    ): Location?

    @Modifying
    @Query("DELETE FROM Location l WHERE l.user = :user")
    fun deleteAllByUser(
        @Param("user") user: User,
    )
}
