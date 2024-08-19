package dnd11th.blooming.api.dto.guide

import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Season
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Month

@Schema(
    name = "Plant Recommended Period Response",
    description = "권장되는 물/비료 주기 응답",
)
data class PlantRecommendedPeriodResponse(
    @field:Schema(description = "며칠마다 물을 줘야 하는지", example = "3")
    val recommendedWaterDay: Int,
    @field:Schema(description = "몇주마다 물을 줘야 하는지", example = "2")
    val recommendedFertilizerWeek: Int,
) {
    companion object {
        fun of(
            plant: Plant,
            month: Month,
        ): PlantRecommendedPeriodResponse {
            val season = Season.getSeason(month)

            val waterInfo =
                when (season) {
                    Season.WINTER -> plant.winterWater
                    else -> plant.springSummerFallWater
                }

            return PlantRecommendedPeriodResponse(
                recommendedWaterDay = waterInfo.periodDay,
                recommendedFertilizerWeek = plant.fertilizer.periodWeek,
            )
        }
    }
}