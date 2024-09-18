package dnd11th.blooming.api.service.guide

import dnd11th.blooming.api.dto.guide.PlantGuideDetailViewResponse
import dnd11th.blooming.api.dto.guide.PlantGuideSimpleViewResponse
import dnd11th.blooming.api.service.guide.provider.StaticPlantDetailMessageProvider
import dnd11th.blooming.api.service.guide.provider.StaticPlantSimpleMessageProvider
import dnd11th.blooming.core.entity.plant.Plant
import org.springframework.stereotype.Component
import java.time.Month

@Component
class StaticPlantMessageFactory(
    private val simpleMessageProvider: StaticPlantSimpleMessageProvider,
    private val detailMessageProvider: StaticPlantDetailMessageProvider,
) : PlantMessageFactory {
    override fun buildTags(plant: Plant): List<String> {
        val tagList = mutableListOf<String>()
        tagList.add(
            plant.difficulty.targetPeople,
        )
        // TODO : 태그 더 넣기
        return tagList
    }

    override fun buildSimpleView(
        plant: Plant,
        month: Month,
    ): PlantGuideSimpleViewResponse = simpleMessageProvider.buildSimpleView(plant, month)

    override fun buildDetailView(plant: Plant): PlantGuideDetailViewResponse = detailMessageProvider.buildDetailView(plant)
}
