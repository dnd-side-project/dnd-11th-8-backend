package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.PlantGuideSimpleResponse
import dnd11th.blooming.api.dto.guide.PlantGuideSimpleViewResponse
import dnd11th.blooming.domain.entity.plant.Fertilizer
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Season
import dnd11th.blooming.domain.entity.plant.Toxicity
import org.springframework.stereotype.Component
import java.time.Month

@Component
class StaticPlantSimpleMessageProvider {
    companion object {
        const val DIFFICULTY_TITLE = "난이도"
        const val WATER_TITLE = "물주기"
        const val PESTS_TITLE = "병해충"
        const val LOCATION_TITLE = "장소"
        const val SIZE_TITLE = "크기"
        const val TOXICITY_TITLE = "독성"
        const val TEMPERATURE_TITLE = "온도"
        const val FERTILIZER_TITLE = "비료"
    }

    fun buildSimpleView(
        plant: Plant,
        month: Month,
    ): PlantGuideSimpleViewResponse =
        PlantGuideSimpleViewResponse(
            difficulty = simpleDifficultyResponse(plant),
            water = simpleWaterResponse(plant, month),
            pests = simplePestsResponse(plant),
            location = simpleLocationResponse(plant),
            size = simpleSizeResponse(plant),
            toxicity = simpleToxicityResponse(plant),
            temperature = simpleTemperatureResponse(plant),
            fertilizer = simpleFertilizerResponse(plant),
        )

    private fun simpleDifficultyResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = DIFFICULTY_TITLE,
            description = makeSimpleDifficultyDescription(plant),
        )

    private fun simpleWaterResponse(
        plant: Plant,
        month: Month,
    ) = PlantGuideSimpleResponse(
        title = WATER_TITLE,
        description = makeSimpleWaterDescription(plant, month),
    )

    private fun simplePestsResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = PESTS_TITLE,
            description = makeSimplePestsDescription(plant),
        )

    private fun simpleLocationResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = LOCATION_TITLE,
            description = makeSimpleLocationDescription(plant),
        )

    private fun simpleSizeResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = SIZE_TITLE,
            description = makeSimpleSizeDescription(plant),
        )

    private fun simpleToxicityResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = TOXICITY_TITLE,
            description = makeSimpleToxicityDescription(plant),
        )

    private fun simpleTemperatureResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = TEMPERATURE_TITLE,
            description = makeSimpleTemperatureDescription(plant),
        )

    private fun simpleFertilizerResponse(plant: Plant) =
        PlantGuideSimpleResponse(
            title = FERTILIZER_TITLE,
            description = makeSimpleFertilizerDescription(plant),
        )

    private fun makeSimpleDifficultyDescription(plant: Plant): String {
        return "${plant.difficulty.targetPeople}추천"
    }

    private fun makeSimpleWaterDescription(
        plant: Plant,
        month: Month,
    ): String {
        val water =
            when (Season.getSeason(month)) {
                Season.WINTER -> plant.winterWater
                else -> plant.springSummerFallWater
            }

        return "${water.waterPerWeek},\n${water.description}할 것"
    }

    private fun makeSimplePestsDescription(plant: Plant): String {
        return plant.pests
    }

    private fun makeSimpleLocationDescription(plant: Plant): String {
        return plant.location.description
    }

    private fun makeSimpleSizeDescription(plant: Plant): String {
        val height = "높이: ${plant.growHeight}cm"
        val width = "넓이: ${plant.growWidth}cm"

        return "${height}\n$width"
    }

    private fun makeSimpleToxicityDescription(plant: Plant): String {
        return when (plant.toxicity) {
            Toxicity.NOT_EXISTS -> "독성 없음"
            Toxicity.EXISTS -> "동물이나 어린이 주위에 두지 말 것"
        }
    }

    private fun makeSimpleTemperatureDescription(plant: Plant): String {
        val growTemperatureDescription = "${plant.growTemperature.lowTemperature}~${plant.growTemperature.hightTemperature}사이"
        val lowestTemperature = "${plant.lowestTemperature.temperature}도 이하는 주의할 것"

        return "${growTemperatureDescription}\n$lowestTemperature"
    }

    private fun makeSimpleFertilizerDescription(plant: Plant): String {
        return when (plant.fertilizer) {
            Fertilizer.DEMANDING -> "봄, 여름에 2~4주 간격으로 주기"
            Fertilizer.NOT_VERY_DEMANDING -> "봄, 여름에 4~8주 간격으로 주기"
        }
    }
}
