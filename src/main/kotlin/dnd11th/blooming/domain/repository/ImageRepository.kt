package dnd11th.blooming.domain.repository

import dnd11th.blooming.api.dto.myplant.MyPlantIdWithImageUrl
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ImageRepository : JpaRepository<Image, Long> {
    fun findAllByMyPlant(myPlant: MyPlant): List<Image>

    @Query(
        """
    SELECT new dnd11th.blooming.api.dto.myplant.MyPlantIdWithImageUrl(i.url, mp.id)
    FROM Image i
    JOIN i.myPlant mp
    WHERE mp IN :myPlants
    AND i.favorite = true
    AND i.id = (
        SELECT MIN(i2.id)
        FROM Image i2
        WHERE i2.myPlant = mp
        AND i2.favorite = true
    )
	""",
    )
    fun findFavoriteImagesForMyPlants(myPlants: List<MyPlant>): List<MyPlantIdWithImageUrl>

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
    @Query(
        """
	DELETE FROM Image i WHERE i.myPlant IN :myPlants
	""",
    )
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
