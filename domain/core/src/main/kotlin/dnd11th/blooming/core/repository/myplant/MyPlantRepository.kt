package dnd11th.blooming.core.repository.myplant

import dnd11th.blooming.core.entity.location.Location
import dnd11th.blooming.core.entity.myplant.MyPlant
import dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl
import dnd11th.blooming.core.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MyPlantRepository : JpaRepository<MyPlant, Long>, MyPlantQueryDslRepository {
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


    @Query(
        """
    SELECT new dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl(
        mp,
        (SELECT i.url
        FROM Image i
        WHERE i.myPlant = mp AND i.favorite = true
        ORDER BY i.updatedDate
        DESC LIMIT 1)
    )
    FROM MyPlant mp
	JOIN FETCH mp.location l
    WHERE mp.user = :user
	""",
    )
    fun findMyPlantAndMostRecentFavoriteImageByUser(
        @Param("user") user: User,
    ): List<MyPlantWithImageUrl>
}
