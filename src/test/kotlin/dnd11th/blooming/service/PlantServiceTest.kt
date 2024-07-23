package dnd11th.blooming.service

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.service.PlantService
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.PlantRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class PlantServiceTest : BehaviorSpec(
    {
        val plantRepsitory = mockk<PlantRepository>()
        val plantService = PlantService(plantRepsitory)

        Context("식물 저장") {
            beforeTest {
                every { plantRepsitory.save(any()) } returns
                    Plant(
                        scientificName = SCIENTIFIC_NAME,
                        name = NAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = true,
                        nutrientsAlarm = false,
                    )
            }
            Given("정상 요청이 왔을 때") {
                val request =
                    PlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        name = NAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = true,
                        nutrientsAlarm = false,
                    )
                When("식물을 저장하면") {
                    val response = plantService.savePlant(request)
                    Then("정상적으로 저장되어야 한다.") {
                        response.id shouldBe 0
                    }
                }
            }
        }

        Context("식물 전체 조회") {
            beforeTest {
                every { plantRepsitory.findAll() } returns
                    listOf(
                        Plant(
                            scientificName = SCIENTIFIC_NAME,
                            name = NAME,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            waterAlarm = true,
                            nutrientsAlarm = false,
                        ),
                        Plant(
                            scientificName = SCIENTIFIC_NAME2,
                            name = NAME2,
                            startDate = START_DATE2,
                            lastWateredDate = LAST_WATERED_DATE2,
                            waterAlarm = true,
                            nutrientsAlarm = false,
                        ),
                    )
            }
            Given("식물을 전체 조회할 때") {
                When("식물을 조회하면") {
                    val response = plantService.findAllPlant()
                    Then("식물 리스트가 조회되어야 한다.") {
                        response.size shouldBe 2
                        response[0].name shouldBe NAME
                        response[0].scientificName shouldBe SCIENTIFIC_NAME
                        response[1].name shouldBe NAME2
                        response[1].scientificName shouldBe SCIENTIFIC_NAME2
                    }
                }
            }
        }
    },
) {
    companion object {
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)

        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NAME2 = "빵빵이"
        val START_DATE2: LocalDate = LocalDate.of(2024, 3, 20)
        val LAST_WATERED_DATE2: LocalDate = LocalDate.of(2024, 7, 20)
    }
}
