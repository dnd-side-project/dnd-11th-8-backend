package dnd11th.blooming.core.repository.image

import dnd11th.blooming.core.entity.image.Image
import dnd11th.blooming.core.entity.myplant.MyPlant
import dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl
import dnd11th.blooming.core.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ImageRepository : JpaRepository<Image, Long> {
    fun findAllByMyPlant(myPlant: MyPlant): List<Image>

    @Query(
        """
    SELECT new dnd11th.blooming.core.entity.myplant.MyPlantWithImageUrl(mp, i.url)
    FROM Image i
    JOIN i.myPlant mp
	JOIN FETCH mp.location l
    WHERE mp.user = :user
    AND i.favorite = true
    AND i.updatedDate = (
        SELECT MAX(i2.updatedDate)
        FROM Image i2
        WHERE i2.myPlant = mp
        AND i2.favorite = true
    )
	""",
    )
    fun findMyPlantAndMostRecentFavoriteImageByUser(
        @Param("user") user: User,
    ): List<MyPlantWithImageUrl>

    @Modifying
    @Query(
        """
	DELETE FROM Image i WHERE i.myPlant = :myPlant
	""",
    )
    fun deleteAllInBatchByMyPlant(
        @Param("myPlant") myPlant: MyPlant,
    )

    @Modifying
    @Query("DELETE FROM Image i WHERE i.myPlant IN :myPlants")
    fun deleteAllByMyPlantIn(
        @Param("myPlants") myPlants: List<MyPlant>,
    )

    @Query(
        """
	SELECT i from Image i WHERE i.id = :imageId and i.myPlant.user = :user
	""",
    )
    fun findByIdAndUser(
        @Param("imageId") imageId: Long,
        @Param("user") user: User,
    ): Image?
}
