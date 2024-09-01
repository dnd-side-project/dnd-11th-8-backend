package dnd11th.blooming.api.service.guide

import dnd11th.blooming.domain.entity.plant.Difficulty
import dnd11th.blooming.domain.entity.plant.Fertilizer
import dnd11th.blooming.domain.entity.plant.GrowLocation
import dnd11th.blooming.domain.entity.plant.GrowTemperature
import dnd11th.blooming.domain.entity.plant.Humidity
import dnd11th.blooming.domain.entity.plant.Light
import dnd11th.blooming.domain.entity.plant.LowestTemperature
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.plant.Toxicity
import dnd11th.blooming.domain.entity.plant.Water
import dnd11th.blooming.domain.repository.PlantRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class GuideServiceIntegrationTest : DescribeSpec() {
    @Autowired
    lateinit var plantRepository: PlantRepository

    @Autowired
    lateinit var guideService: GuideService

    init {
        afterEach {
            plantRepository.deleteAllInBatch()
        }

        describe("식물 종류 찾기") {
            plantRepository.saveAll(
                listOf(
                    makePlant("몬스테라 델리시오사", "monstera"),
                    makePlant("몬스테라델리시오사", "monstera"),
                    makePlant("몬스테라", "monstera"),
                    makePlant("델리시오사몬스테라", "monstera"),
                    makePlant("델리시오사 몬스테라", "monstera"),
                ),
            )
            context("한국 이름으로 식물을 검색하면") {
                val findPlantList = guideService.findPlantList("몬스테라")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 5
                }
            }
            context("글자가 완성되지 않은 채 식물을 검색하면") {
                val findPlantList = guideService.findPlantList("몬스테ㄹ")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 5
                }
            }
        }
    }

    fun makePlant(
        korName: String,
        engName: String,
    ): Plant {
        return Plant(
            korName = korName,
            engName = engName,
            springWater = Water.MOIST,
            summerWater = Water.MOIST,
            fallWater = Water.MOIST,
            winterWater = Water.MOIST,
            growHeight = 100,
            growWidth = 100,
            light = Light.LOW_MEDIUM_HIGH,
            growTemperature = GrowTemperature.GROW_TEMPERATURE_16_20,
            toxicity = Toxicity.NOT_EXISTS,
            humidity = Humidity.HUMIDITY_0_40,
            fertilizer = Fertilizer.HIGH_DEMAND,
            lowestTemperature = LowestTemperature.LOWEST_TEMPERATURE_10,
            difficulty = Difficulty.MEDIUM,
            location = GrowLocation.ROOM_DARK,
            pests = "해충1, 해충2",
            imageUrl = "image.com",
        )
    }
}
