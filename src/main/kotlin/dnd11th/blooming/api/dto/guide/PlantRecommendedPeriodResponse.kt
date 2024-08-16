package dnd11th.blooming.api.dto.guide

import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Season
import java.time.Month

data class PlantRecommendedPeriodResponse(
    val recommendedWaterDay: Int,
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
