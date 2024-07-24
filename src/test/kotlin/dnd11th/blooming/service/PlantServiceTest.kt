package dnd11th.blooming.service

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.service.PlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Plant
import dnd11th.blooming.domain.repository.PlantRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class PlantServiceTest : BehaviorSpec(
    {
        val plantRepsitory = mockk<PlantRepository>()
        val plantService = PlantService(plantRepsitory)

        Context("식물 저장") {
            every { plantRepsitory.save(any()) } returns
                Plant(
                    scientificName = SCIENTIFIC_NAME,
                    name = NAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    waterAlarm = true,
                    nutrientsAlarm = false,
                )
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
            Given("시작날짜가 과거인 요청으로") {
                val request =
                    PlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        name = NAME,
                        startDate = FUTURE_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = true,
                        nutrientsAlarm = false,
                    )
                When("식물을 저장하면") {
                    Then("InvalidDateException 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<InvalidDateException> {
                                plantService.savePlant(request)
                            }
                        exception.message shouldBe "올바르지 않은 날짜입니다."
                        exception.errorType shouldBe ErrorType.INVALID_DATE
                    }
                }
            }
            Given("마지막으로 물 준 날짜가 과거인 요청으로") {
                val request =
                    PlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        name = NAME,
                        startDate = START_DATE,
                        lastWateredDate = FUTURE_DATE,
                        waterAlarm = true,
                        nutrientsAlarm = false,
                    )
                When("식물을 저장하면") {
                    Then("InvalidDateException 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<InvalidDateException> {
                                plantService.savePlant(request)
                            }
                        exception.message shouldBe "올바르지 않은 날짜입니다."
                        exception.errorType shouldBe ErrorType.INVALID_DATE
                    }
                }
            }
        }

        Context("식물 전체 조회") {
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

        Context("식물 상세 조회") {
            every { plantRepsitory.findByIdOrNull(ID) } returns
                Plant(
                    scientificName = SCIENTIFIC_NAME,
                    name = NAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    waterAlarm = true,
                    nutrientsAlarm = false,
                )
            every { plantRepsitory.findByIdOrNull(not(eq(ID))) } returns
                null
            Given("존재하는 ID로") {
                When("상세 조회하면") {
                    val response = plantService.findPlantDetail(ID)
                    Then("상세 정보가 조회되어야 한다.") {
                        response.name shouldBe NAME
                        response.scientificName shouldBe SCIENTIFIC_NAME
                        response.startDate shouldBe START_DATE
                        response.lastWatedDate shouldBe LAST_WATERED_DATE
                    }
                }
            }
            Given("존재하지 않는 ID로") {
                When("상세 조회하면") {
                    Then("PlantNotFoundException 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<NotFoundException> {
                                plantService.findPlantDetail(ID2)
                            }
                        exception.message shouldBe "존재하지 않는 식물입니다."
                        exception.errorType shouldBe ErrorType.NOT_FOUND_PLANT_ID
                    }
                }
            }
        }
    },
) {
    companion object {
        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)

        const val ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NAME2 = "빵빵이"
        val START_DATE2: LocalDate = LocalDate.of(2024, 3, 20)
        val LAST_WATERED_DATE2: LocalDate = LocalDate.of(2024, 7, 20)

        val FUTURE_DATE: LocalDate = LocalDate.of(5000, 5, 17)
    }
}
