package dnd11th.blooming.core.repository.region

import dnd11th.blooming.core.entity.region.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RegionRepository : JpaRepository<Region, Int> {
    @Query(
        value = """
        SELECT *
        FROM region
        WHERE MATCH(name) AGAINST(:searchQuery IN BOOLEAN MODE)
        LIMIT :limit
        """,
        nativeQuery = true,
    )
    fun findByNameContaining(
        @Param("searchQuery") searchQuery: String,
        @Param("limit") limit: Int,
    ): List<Region>
}
