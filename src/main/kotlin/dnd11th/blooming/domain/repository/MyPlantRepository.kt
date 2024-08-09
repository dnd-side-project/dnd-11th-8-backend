package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import org.springframework.data.jpa.repository.JpaRepository

interface MyPlantRepository : JpaRepository<MyPlant, Long> {
    fun findAllByLocationOrderByCreatedDateAsc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByCreatedDateDesc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByLastWateredDateAsc(location: Location?): List<MyPlant>

    fun findAllByLocationOrderByLastWateredDateDesc(location: Location?): List<MyPlant>
}
