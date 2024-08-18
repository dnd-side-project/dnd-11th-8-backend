package dnd11th.blooming.api.service.region

import dnd11th.blooming.api.dto.region.RegionResponse
import dnd11th.blooming.domain.entity.region.Region
import dnd11th.blooming.domain.repository.region.RegionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RegionService(
    private val regionRepository: RegionRepository,
) {
    companion object {
        private const val DEFAULT_PAGE_NUMBER = 0
        private const val DEFAULT_PAGE_SIZE = 30
    }

    fun findRegion(name: String): List<RegionResponse> {
        val pageable: Pageable = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)
        val regions: List<Region> = regionRepository.findByNameContaining(name, pageable)
        return regions.stream().map { region -> RegionResponse.from(region) }.toList()
    }
}
