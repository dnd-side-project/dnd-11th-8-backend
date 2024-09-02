package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.DetailPestsResponse
import dnd11th.blooming.common.util.getEunOrNun
import dnd11th.blooming.domain.entity.plant.Plant
import org.springframework.stereotype.Component

@Component
class PestsDetailProvider {
    companion object {
        const val PESTS_TITLE = "병해충"
    }

    fun createDetailPestsResponse(plant: Plant): DetailPestsResponse {
        return DetailPestsResponse(
            title = PESTS_TITLE,
            description = makeDetailPestsDescription(plant),
        )
    }

    private fun makeDetailPestsDescription(plant: Plant): String {
        val eunOrNun = plant.korName.getEunOrNun()

        return "${plant.korName}$eunOrNun ${plant.pests}등에 취약해요. 과습과 건조를 피하고, 환기를 하여 예방하세요."
    }
}
