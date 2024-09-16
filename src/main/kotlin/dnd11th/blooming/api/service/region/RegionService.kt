package dnd11th.blooming.api.service.region

import dnd11th.blooming.api.dto.region.RegionResponse
import dnd11th.blooming.domain.entity.region.Region
import dnd11th.blooming.domain.repository.region.RegionRepository
import org.springframework.stereotype.Service

@Service
class RegionService(
    private val regionRepository: RegionRepository,
) {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 30
    }

    fun findRegion(name: String): List<RegionResponse> {
        val searchQuery = createSearchQuery(name)
        val regions: List<Region> = regionRepository.findByNameContaining2(searchQuery, DEFAULT_PAGE_SIZE)
        return regions.stream().map { region -> RegionResponse.from(region) }.toList()
    }

    fun createSearchQuery(input: String): String {
        return input.split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { "+$it" }
    }
}
