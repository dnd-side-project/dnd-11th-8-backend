package dnd11th.blooming.api.service.guide

import dnd11th.blooming.domain.core.entity.plant.Difficulty
import dnd11th.blooming.domain.core.entity.plant.Fertilizer
import dnd11th.blooming.domain.core.entity.plant.GrowLocation
import dnd11th.blooming.domain.core.entity.plant.GrowTemperature
import dnd11th.blooming.domain.core.entity.plant.Humidity
import dnd11th.blooming.domain.core.entity.plant.Light
import dnd11th.blooming.domain.core.entity.plant.LowestTemperature
import dnd11th.blooming.domain.core.entity.plant.Plant
import dnd11th.blooming.domain.core.entity.plant.Toxicity
import dnd11th.blooming.domain.core.entity.plant.Water
import dnd11th.blooming.domain.core.repository.plant.PlantRepository
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
            beforeTest {
                plantRepository.saveAll(
                    listOf(
                        makePlant("몬스테라 델리시오사", "monstera"),
                        makePlant("몬스테라델리시오사", "monstera"),
                        makePlant("몬스테라", "monstera"),
                        makePlant("델리시오사몬스테라", "monstera"),
                        makePlant("델리시오사 몬스테라", "monstera"),
                        makePlant("선인장", "suninjang"),
                        makePlant("전혀 다른 식물", "kiki"),
                    ),
                )
            }

            context("한글 이름으로 식물을 검색하면") {
                val findPlantList = guideService.findPlantList("몬스테라")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 3
                }
            }
            context("초성만 적고 식물을 검색하면") {
                val findPlantList = guideService.findPlantList("몬스테ㄹ")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 3
                }
            }
            context("중성까지 적고 식물을 검색하면") {
                val findPlantList = guideService.findPlantList("선인자")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 1
                }
            }
            context("영어명으로 검색하면") {
                val findPlantList = guideService.findPlantList("monstera")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 5
                }
            }
            context("영어명의 앞부분으로만 검색하면") {
                val findPlantList = guideService.findPlantList("monst")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 5
                }
            }
            context("영어명의 뒷부분으로만 검색하면") {
                val findPlantList = guideService.findPlantList("era")
                it("조회되면 안된다.") {
                    findPlantList.size shouldBe 0
                }
            }
            context("영어명의 중간부분으로 검색하면") {
                val findPlantList = guideService.findPlantList("ste")
                it("제대로 모든 식물이 조회되어야 한다.") {
                    findPlantList.size shouldBe 0
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
