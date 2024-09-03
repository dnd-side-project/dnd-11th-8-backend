package dnd11th.blooming.domain.repository.myplant

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MyPlantRepository : JpaRepository<MyPlant, Long>, MyPlantQueryDslRepository {
    fun findByIdAndUser(
        id: Long,
        user: User,
    ): MyPlant?

    @Query("SELECT p FROM MyPlant p JOIN FETCH p.location WHERE p.user = :user")
    fun findAllByUserJoinFetchLocation(
        @Param("user") user: User,
    ): List<MyPlant>

    fun findAllByUser(user: User)

    fun countByUser(user: User): Int

    @Modifying
    @Query("UPDATE MyPlant p SET p.location = NULL WHERE p.location = :location")
    fun nullifyLocationByLocation(
        @Param("location") location: Location,
    )
}
