package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
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
}
