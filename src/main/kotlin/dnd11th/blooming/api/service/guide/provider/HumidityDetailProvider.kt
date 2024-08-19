package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.DetailHumidityResponse
import dnd11th.blooming.common.util.KoreanUtil.Companion.getEunOrNun
import dnd11th.blooming.domain.entity.plant.Plant
import org.springframework.stereotype.Component

@Component
class HumidityDetailProvider {
    companion object {
        const val HUMIDITY_TITLE = "습도"
        const val HUMIDITY_DETAIL_ADDITION = "습도를 높이기 위해 자주 분무하거나, 식물 주변에 물그릇을 놓아 습도를 유지할 수 있어요. 가습기를 사용하는 것도 좋아요."
    }

    fun createDetailHumidityResponse(plant: Plant): DetailHumidityResponse {
        return DetailHumidityResponse(
            title = HUMIDITY_TITLE,
            description = makeDetailHumidityDescription(plant),
            addition = makeDetailHumidityAddition(),
        )
    }

    private fun makeDetailHumidityDescription(plant: Plant): String {
        val eunOrNun = getEunOrNun(plant.korName)

        return "${plant.korName}$eunOrNun ${plant.humidity.humidityLevel}을 좋아해요.\n" +
            "${plant.humidity.displayName}의 습도가 가장 이상적이에요."
    }

    private fun makeDetailHumidityAddition(): String {
        return HUMIDITY_DETAIL_ADDITION
    }
}
