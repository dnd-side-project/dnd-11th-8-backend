package dnd11th.blooming.domain.repository.region

import dnd11th.blooming.domain.entity.region.Region
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RegionRepository : JpaRepository<Region, Int> {
    fun findByNameContaining(
        name: String,
        pageable: Pageable,
    ): List<Region>
}
