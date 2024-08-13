package dnd11th.blooming.domain.repository.region

import dnd11th.blooming.domain.entity.region.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RegionRepository : JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE r.cityProvince.id = :ctpId AND r.siGunGu.id = :sigId")
    fun findByCtpIdAndSigId(
        ctpId: Int,
        sigId: Int,
    ): Region?
}
