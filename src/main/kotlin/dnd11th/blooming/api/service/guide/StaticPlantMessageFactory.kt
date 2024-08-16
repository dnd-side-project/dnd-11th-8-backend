package dnd11th.blooming.api.service.guide

import dnd11th.blooming.api.dto.guide.DetailHumidityResponse
import dnd11th.blooming.api.dto.guide.DetailLightResponse
import dnd11th.blooming.api.dto.guide.DetailPestsResponse
import dnd11th.blooming.api.dto.guide.DetailToxicityResponse
import dnd11th.blooming.api.dto.guide.DetailWaterResponse
import dnd11th.blooming.api.dto.guide.PlantGuideDetailViewResponse
import dnd11th.blooming.api.dto.guide.PlantGuideSimpleResponse
import dnd11th.blooming.api.dto.guide.PlantGuideSimpleViewResponse
import dnd11th.blooming.domain.entity.plant.Fertilizer
import dnd11th.blooming.domain.entity.plant.Light
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Season
import dnd11th.blooming.domain.entity.plant.Toxicity
import org.springframework.stereotype.Component
import java.time.Month

@Component
class StaticPlantMessageFactory : PlantMessageFactory {
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
    ): PlantGuideSimpleViewResponse {
        return PlantGuideSimpleViewResponse(
            difficulty =
                PlantGuideSimpleResponse(
                    title = DIFFICULTY_TITLE,
                    description = makeSimpleDifficultyDescription(plant),
                ),
            water =
                PlantGuideSimpleResponse(
                    title = WATER_TITLE,
                    description = makeSimpleWaterDescription(plant, month),
                ),
            pests =
                PlantGuideSimpleResponse(
                    title = PESTS_TITLE,
                    description = makeSimplePestsDescription(plant),
                ),
            location =
                PlantGuideSimpleResponse(
                    title = LOCATION_TITLE,
                    description = makeSimpleLocationDescription(plant),
                ),
            size =
                PlantGuideSimpleResponse(
                    title = SIZE_TITLE,
                    description = makeSimpleSizeDescription(plant),
                ),
            toxicity =
                PlantGuideSimpleResponse(
                    title = TOXICITY_TITLE,
                    description = makeSimpleToxicityDescription(plant),
                ),
            temperature =
                PlantGuideSimpleResponse(
                    title = TEMPERATURE_TITLE,
                    description = makeSimpleTemperatureDescription(plant),
                ),
            fertilizer =
                PlantGuideSimpleResponse(
                    title = FERTILIZER_TITLE,
                    description = makeSimpleFertilizerDescription(plant),
                ),
        )
    }

    override fun buildDetailView(plant: Plant): PlantGuideDetailViewResponse {
        return PlantGuideDetailViewResponse(
            water =
                DetailWaterResponse(
                    title = WATER_TITLE,
                    springsummerfallSubTitle = SPRING_SUMMER_FALL_TITLE,
                    springsummerfallDescription = makeDetailSpringSummerFallDescription(plant),
                    winterSubTitle = WINTER_TITLE,
                    winterDescription = makeDetailWinterDescription(plant),
                    addition = makeDetailWaterAddition(),
                ),
            light =
                DetailLightResponse(
                    title = LIGHT_TITLE,
                    lightSubTitle = LIGHT_SUB_TITLE,
                    lightDescription = makeDetailLightDescription(plant),
                    addition = makeDetailLightAddition(plant),
                ),
            humidity =
                DetailHumidityResponse(
                    title = HUMIDITY_TITLE,
                    description = makeDetailHumidityDescription(plant),
                    addition = makeDetailHumidityAddition(),
                ),
            toxicity =
                DetailToxicityResponse(
                    title = TOXICITY_TITLE,
                    description = makeDetailToxicityDescription(plant),
                ),
            pests =
                DetailPestsResponse(
                    title = PESTS_TITLE,
                    description = makeDetailPestsDescription(plant),
                ),
        )
    }

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
        return plant.location
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
        val growTemperatureDescription = "${plant.growTemperature.displayName}사이"
        val lowestTemperature = "${plant.lowestTemperature.displayName} 이하는 주의할 것"

        return "${growTemperatureDescription}\n$lowestTemperature"
    }

    private fun makeSimpleFertilizerDescription(plant: Plant): String {
        return when (plant.fertilizer) {
            Fertilizer.DEMANDING -> "봄, 여름에 2~4주 간격으로 주기"
            Fertilizer.NOT_VERY_DEMANDING -> "봄, 여름에 4~8주 간격으로 주기"
        }
    }

    private fun makeDetailSpringSummerFallDescription(plant: Plant): String {
        val waterDescription = "${plant.springSummerFallWater.description}해주세요."
        val perWeekDescription = "${plant.springSummerFallWater.waterPerWeek} 정도가 적당해요."
        return "$waterDescription $perWeekDescription"
    }

    private fun makeDetailWinterDescription(plant: Plant): String {
        return "${plant.springSummerFallWater.waterPerWeek} 정도가 적당해요."
    }

    private fun makeDetailWaterAddition(): String {
        return WATER_DETAIL_ADDITION
    }

    private fun makeDetailLightDescription(plant: Plant): String {
        return plant.light.apiName
    }

    private fun makeDetailLightAddition(plant: Plant): String {
        val eunOrNun = getEunOrNun(plant.korName)

        val description =
            when (plant.light) {
                Light.LOW -> "빛을 많이 받지 않아도 괜찮습니다."
                Light.MEDIUM -> "간접적인 밝은 빛을 가장 좋아합니다.\n단, 직사광선은 잎을 태울 수 있으므로 피하는 것이 좋아요."
                Light.HIGH -> "직접적인 밝은 빛을 가장 좋아합니다."
            }

        return "${plant.korName}$eunOrNun $description"
    }

    private fun makeDetailHumidityDescription(plant: Plant): String {
        val eunOrNun = getEunOrNun(plant.korName)

        return "${plant.korName}$eunOrNun ${plant.humidity.humidityLevel}을 좋아해요.\n" +
            "${plant.humidity.displayName}의 습도가 가장 이상적이에요."
    }

    private fun makeDetailHumidityAddition(): String {
        return HUMIDITY_DETAIL_ADDITION
    }

    private fun makeDetailToxicityDescription(plant: Plant): String {
        val eunOrNun = getEunOrNun(plant.korName)

        val description =
            when (plant.toxicity) {
                Toxicity.NOT_EXISTS -> "독성이 없어요."
                Toxicity.EXISTS -> "독성이 있어 반려동물과 어린아이들이 먹지 않도록 주의해야 해요."
            }

        return "${plant.korName}$eunOrNun $description"
    }

    private fun makeDetailPestsDescription(plant: Plant): String {
        val eunOrNun = getEunOrNun(plant.korName)

        return "${plant.korName}$eunOrNun ${plant.pests}등에 취약해요. 과습과 건조를 피하고, 환기를 하여 예방하세요."
    }

    private fun getEunOrNun(str: String): String {
        return when (isKorStringEndsWithBatchim(str)) {
            true -> "은"
            false -> "는"
        }
    }

    private fun isKorStringEndsWithBatchim(str: String): Boolean {
        if (str.isBlank()) return false
        val lastChar = str.last()
        if (lastChar !in '가'..'힣') return false
        return (lastChar.code - '가'.code) % 28 != 0
    }

    companion object {
        const val DIFFICULTY_TITLE = "난이도"
        const val WATER_TITLE = "물주기"
        const val SPRING_SUMMER_FALL_TITLE = "봄,여름,가을"
        const val WINTER_TITLE = "겨울"
        const val LIGHT_TITLE = "빛"
        const val LIGHT_SUB_TITLE = "광요구도"
        const val HUMIDITY_TITLE = "습도"
        const val PESTS_TITLE = "병해충"
        const val LOCATION_TITLE = "장소"
        const val SIZE_TITLE = "크기"
        const val TOXICITY_TITLE = "독성"
        const val TEMPERATURE_TITLE = "온도"
        const val FERTILIZER_TITLE = "비료"
        const val WATER_DETAIL_ADDITION = "빗물을 주면 토양이 산성화 될 수도 있어요. 따뜻한 물을 사용하고 더운 여름에는 젖은 천으로 잎을 닦아주고, 분무해주세요."
        const val HUMIDITY_DETAIL_ADDITION = "습도를 높이기 위해 자주 분무하거나, 식물 주변에 물그릇을 놓아 습도를 유지할 수 있어요. 가습기를 사용하는 것도 좋아요."
    }
}
