package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.MyPlant
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long> {
    fun findAllByMyPlant(myPlant: MyPlant): List<Image>

    fun deleteAllByMyPlant(myPlant: MyPlant)
}
