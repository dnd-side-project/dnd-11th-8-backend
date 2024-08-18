package dnd11th.blooming.api.dto.region

import dnd11th.blooming.domain.entity.region.Region

data class RegionResponse(
    val id: Int,
    val name: String,
) {
    companion object {
        fun from(region: Region): RegionResponse {
            return RegionResponse(region.id, region.name)
        }
    }
}
