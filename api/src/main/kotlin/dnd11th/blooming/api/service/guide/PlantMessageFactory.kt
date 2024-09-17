package dnd11th.blooming.api.service.guide

import dnd11th.blooming.api.dto.guide.PlantGuideDetailViewResponse
import dnd11th.blooming.api.dto.guide.PlantGuideSimpleViewResponse
import dnd11th.blooming.core.entity.plant.Plant
import java.time.Month

interface PlantMessageFactory {
    fun buildTags(plant: Plant): List<String>

    fun buildSimpleView(
        plant: Plant,
        month: Month,
    ): PlantGuideSimpleViewResponse

    fun buildDetailView(plant: Plant): PlantGuideDetailViewResponse
}
