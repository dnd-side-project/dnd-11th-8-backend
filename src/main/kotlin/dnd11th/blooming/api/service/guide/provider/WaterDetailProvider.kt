package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.DetailWaterResponse
import dnd11th.blooming.domain.entity.plant.Plant
import org.springframework.stereotype.Component

@Component
class WaterDetailProvider {
    companion object {
        const val WATER_TITLE = "물주기"
        const val SPRING_SUMMER_FALL_TITLE = "봄,여름,가을"
        const val WINTER_TITLE = "겨울"
        const val WATER_DETAIL_ADDITION = "빗물을 주면 토양이 산성화 될 수도 있어요. 따뜻한 물을 사용하고 더운 여름에는 젖은 천으로 잎을 닦아주고, 분무해주세요."
    }

    fun createDetailWaterResponse(plant: Plant): DetailWaterResponse {
        return DetailWaterResponse(
            title = WATER_TITLE,
            springsummerfallSubTitle = SPRING_SUMMER_FALL_TITLE,
            springsummerfallDescription = makeDetailSpringSummerFallDescription(plant),
            winterSubTitle = WINTER_TITLE,
            winterDescription = makeDetailWinterDescription(plant),
            addition = makeDetailWaterAddition(),
        )
    }

    private fun makeDetailSpringSummerFallDescription(plant: Plant): String {
        val waterDescription = "${plant.springSummerFallWater.description}해주세요."
        val perWeekDescription = "일주일에 ${plant.springSummerFallWater.waterPerWeek}번 정도가 적당해요."
        return "$waterDescription $perWeekDescription"
    }

    private fun makeDetailWinterDescription(plant: Plant): String {
        return "일주일에 ${plant.winterWater.waterPerWeek}번 정도가 적당해요."
    }

    private fun makeDetailWaterAddition(): String {
        return WATER_DETAIL_ADDITION
    }
}
