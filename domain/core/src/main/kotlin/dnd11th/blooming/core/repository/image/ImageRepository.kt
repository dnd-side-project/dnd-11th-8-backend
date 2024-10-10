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

    @Query(
        """
	SELECT i from Image i WHERE i.id In :imageIds and i.myPlant.user = :user
	""",
    )
    fun findByUserAndIdsIn(
        @Param("imageIds") imageIds: List<Long>,
        @Param("user") user: User,
    ): List<Image>
}
