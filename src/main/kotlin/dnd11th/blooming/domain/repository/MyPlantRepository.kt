package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MyPlantRepository : JpaRepository<MyPlant, Long> {
    fun findAllByLocationOrderByCreatedDateAsc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByCreatedDateDesc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByLastWateredDateAsc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByLastWateredDateDesc(location: Location?): List<MyPlant>

    fun existsByLocationId(locationId: Long): Boolean

    fun findAllByLocationId(locationId: Long): List<MyPlant>

    @Modifying
    @Query(
        """
		DELETE FROM MyPlant mp where mp.location.id = :locationId
	""",
    )
    fun deleteAllByLocationId(
        @Param("locationId") locationId: Long,
    )

    fun countByUser(user: User): Int
}
