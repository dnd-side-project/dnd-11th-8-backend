package dnd11th.blooming.api.service.region

import dnd11th.blooming.api.dto.region.RegionResponse
import dnd11th.blooming.domain.core.entity.region.Region
import dnd11th.blooming.domain.core.repository.region.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RegionService(
    private val regionRepository: RegionRepository,
) {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 30
        private val jamoSet =
            setOf(
                'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ',
                'ㅏ', 'ㅑ', 'ㅓ', 'ㅕ', 'ㅗ', 'ㅛ', 'ㅜ', 'ㅠ', 'ㅡ', 'ㅣ',
            )
    }

    fun findRegion(name: String): List<RegionResponse> {
        val searchQuery = createSearchQuery(name)
        val regions: List<Region> = regionRepository.findByNameContaining(searchQuery, DEFAULT_PAGE_SIZE)
        return regions.stream().map { region -> RegionResponse.from(region) }.toList()
    }

    fun createSearchQuery(input: String): String {
        return input
            .removeLastJamoIfOneWord()
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { "+$it" }
    }

    fun String.removeLastJamoIfOneWord(): String {
        if (this.length > 1 && this.last() in jamoSet) {
            return this.dropLast(1)
        }
        return this
    }
}
