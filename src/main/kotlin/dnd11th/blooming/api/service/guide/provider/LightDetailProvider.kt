package dnd11th.blooming.api.service.guide.provider

import dnd11th.blooming.api.dto.guide.DetailLightResponse
import dnd11th.blooming.common.util.getEunOrNun
import dnd11th.blooming.domain.core.entity.plant.Light
import dnd11th.blooming.domain.core.entity.plant.Plant
import org.springframework.stereotype.Component

@Component
class LightDetailProvider {
    companion object {
        const val LIGHT_TITLE = "빛"
        const val LIGHT_SUB_TITLE = "광요구도"
    }

    fun createDetailLightResponse(plant: Plant): DetailLightResponse {
        return DetailLightResponse(
            title = LIGHT_TITLE,
            lightSubTitle = LIGHT_SUB_TITLE,
            lightDescription = makeDetailLightDescription(plant),
            addition = makeDetailLightAddition(plant),
        )
    }

    private fun makeDetailLightDescription(plant: Plant): String {
        return plant.light.apiName
    }

    private fun makeDetailLightAddition(plant: Plant): String {
        val eunOrNun = plant.korName.getEunOrNun()

        val description =
            when (plant.light) {
                Light.LOW, Light.LOW_MEDIUM, Light.LOW_MEDIUM_HIGH -> "빛을 많이 받지 않아도 괜찮습니다."
                Light.MEDIUM, Light.MEDIUM_HIGH -> "간접적인 밝은 빛을 가장 좋아합니다.\n단, 직사광선은 잎을 태울 수 있으므로 피하는 것이 좋아요."
                Light.HIGH -> "직접적인 밝은 빛을 가장 좋아합니다."
            }

        return "${plant.korName}$eunOrNun $description"
    }
}
