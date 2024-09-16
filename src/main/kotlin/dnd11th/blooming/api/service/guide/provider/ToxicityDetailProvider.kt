package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.DetailToxicityResponse
import dnd11th.blooming.common.util.getEunOrNun
import dnd11th.blooming.domain.core.entity.plant.Plant
import dnd11th.blooming.domain.core.entity.plant.Toxicity
import org.springframework.stereotype.Component

@Component
class ToxicityDetailProvider {
    companion object {
        const val TOXICITY_TITLE = "독성"
    }

    fun createDetailToxicityResponse(plant: Plant): DetailToxicityResponse {
        return DetailToxicityResponse(
            title = TOXICITY_TITLE,
            description = makeDetailToxicityDescription(plant),
        )
    }

    private fun makeDetailToxicityDescription(plant: Plant): String {
        val eunOrNun = plant.korName.getEunOrNun()

        val description =
            when (plant.toxicity) {
                Toxicity.NOT_EXISTS -> "독성이 없어요."
                Toxicity.EXISTS -> "독성이 있어 반려동물과 어린아이들이 먹지 않도록 주의해야 해요."
            }

        return "${plant.korName}$eunOrNun $description"
    }
}
