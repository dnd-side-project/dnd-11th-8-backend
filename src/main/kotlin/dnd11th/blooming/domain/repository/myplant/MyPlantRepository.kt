package dnd11th.blooming.domain.repository.myplant

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MyPlantRepository : JpaRepository<MyPlant, Long>, MyPlantCustomRepository {
    fun findAllByLocationAndUserOrderByCreatedDateAsc(
        location: Location?,
        user: User,
    ): List<MyPlant>

    fun findAllByLocationAndUserOrderByCreatedDateDesc(
        location: Location?,
        user: User,
    ): List<MyPlant>

    fun findAllByLocationAndUserOrderByLastWateredDateAsc(
        location: Location?,
        user: User,
    ): List<MyPlant>

    fun findAllByLocationAndUserOrderByLastWateredDateDesc(
        location: Location?,
        user: User,
    ): List<MyPlant>

    fun findByIdAndUser(
        id: Long,
        user: User,
    ): MyPlant?

    fun findAllByLocation(location: Location): List<MyPlant>

    fun findAllByUser(user: User): List<MyPlant>

    @Modifying
    @Query(
        """
		DELETE FROM MyPlant mp where mp.location = :location
	""",
    )
    fun deleteAllByLocation(
        @Param("location") location: Location,
    )

    fun countByUser(user: User): Int

    @Modifying
    @Query("UPDATE MyPlant p SET p.location = NULL WHERE p.location = :location")
    fun nullifyLocationByLocation(
        @Param("location") location: Location,
    )
}
