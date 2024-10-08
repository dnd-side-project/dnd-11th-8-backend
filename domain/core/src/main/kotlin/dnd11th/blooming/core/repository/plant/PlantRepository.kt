package dnd11th.blooming.core.repository.plant

import dnd11th.blooming.core.entity.plant.Plant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PlantRepository : JpaRepository<Plant, Long> {
    @Query("SELECT p FROM Plant p WHERE p.decomposedKorName LIKE :keyword% OR p.engName LIKE :keyword%")
    fun findByDecomposedNameLike(
        @Param("keyword") keyword: String,
    ): List<Plant>
}
