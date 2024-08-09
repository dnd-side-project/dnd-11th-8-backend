package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.MyPlant
import org.springframework.data.jpa.repository.JpaRepository

interface MyPlantRepository : JpaRepository<MyPlant, Long> {
    fun findAllByOrderByCreatedDateAsc(): List<MyPlant>

    fun findAllByOrderByCreatedDateDesc(): List<MyPlant>

    fun findAllByOrderByLastWateredDateAsc(): List<MyPlant>

    fun findAllByOrderByLastWateredDateDesc(): List<MyPlant>
}
