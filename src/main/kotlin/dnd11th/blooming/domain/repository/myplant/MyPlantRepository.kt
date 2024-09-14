package dnd11th.blooming.domain.repository.myplant

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MyPlantRepository : JpaRepository<MyPlant, Long> {
    fun findByIdAndUser(
        id: Long,
        user: User,
    ): MyPlant?

    fun findAllByUser(user: User): List<MyPlant>

    fun countByUser(user: User): Int

    @Modifying
    @Query("UPDATE MyPlant p SET p.location = NULL WHERE p.location = :location")
    fun nullifyLocationByLocation(
        @Param("location") location: Location,
    )

    @Modifying
    @Query("DELETE FROM MyPlant p WHERE p.user = :user")
    fun deleteAllByUser(
        @Param("user") user: User,
    )
}
