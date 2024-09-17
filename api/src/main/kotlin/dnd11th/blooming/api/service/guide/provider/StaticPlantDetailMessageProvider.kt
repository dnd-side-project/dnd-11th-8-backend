package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.PlantGuideDetailViewResponse
import dnd11th.blooming.core.entity.plant.Plant
import org.springframework.stereotype.Component

@Component
class StaticPlantDetailMessageProvider(
    private val waterDetailProvider: WaterDetailProvider,
    private val lightDetailProvider: LightDetailProvider,
    private val humidityDetailProvider: HumidityDetailProvider,
    private val toxicityDetailProvider: ToxicityDetailProvider,
    private val pestsDetailProvider: PestsDetailProvider,
) {
    fun buildDetailView(plant: Plant): PlantGuideDetailViewResponse =
        PlantGuideDetailViewResponse(
            water = waterDetailProvider.createDetailWaterResponse(plant),
            light = lightDetailProvider.createDetailLightResponse(plant),
            humidity = humidityDetailProvider.createDetailHumidityResponse(plant),
            toxicity = toxicityDetailProvider.createDetailToxicityResponse(plant),
            pests = pestsDetailProvider.createDetailPestsResponse(plant),
        )
}
