package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.plant.Plant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PlantRepository : JpaRepository<Plant, Long> {
    @Query("SELECT p FROM Plant p WHERE p.korName LIKE %:name% OR p.engName LIKE %:name%")
    fun findAllByNameContaining(
        @Param("name") name: String,
    ): List<Plant>
}
