package dnd11th.blooming.service

import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.service.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.MyPlantRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class PlantServiceTest : BehaviorSpec(
    {
        val plantRepsitory = mockk<MyPlantRepository>()
        val myPlantService = MyPlantService(plantRepsitory)

        Context("내 식물 저장") {
            every { plantRepsitory.save(any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    Alarm(
                        waterAlarm = true,
                        waterPeriod = 5,
                        nutrientsAlarm = false,
                        nutrientsPeriod = 30,
                        repotAlarm = true,
                        repotPeriod = 60,
                    ),
                )
            Given("정상 요청으로") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = true,
                        waterPeriod = 60,
                        nutrientsAlarm = null,
                        nutrientsPeriod = null,
                        repotAlarm = true,
                        repotPeriod = null,
                    )
                When("내 식물을 저장하면") {
                    val response = myPlantService.savePlant(request)
                    Then("정상적으로 저장되어야 한다.") {
                        response.id shouldBe 0
                    }
                }
            }
            Given("시작날짜가 미래인 요청으로") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = FUTURE_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = true,
                        waterPeriod = 60,
                        nutrientsAlarm = null,
                        nutrientsPeriod = null,
                        repotAlarm = true,
                        repotPeriod = null,
                    )
                When("내 식물을 저장하면") {
                    Then("InvalidDateException 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<InvalidDateException> {
                                myPlantService.savePlant(request)
                            }
                        exception.message shouldBe "올바르지 않은 날짜입니다."
                        exception.errorType shouldBe ErrorType.INVALID_DATE
                    }
                }
            }
            Given("마지막으로 물 준 날짜가 미래인 요청으로") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = FUTURE_DATE,
                        waterAlarm = true,
                        waterPeriod = 60,
                        nutrientsAlarm = null,
                        nutrientsPeriod = null,
                        repotAlarm = true,
                        repotPeriod = null,
                    )
                When("내 식물을 저장하면") {
                    Then("InvalidDateException 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<InvalidDateException> {
                                myPlantService.savePlant(request)
                            }
                        exception.message shouldBe "올바르지 않은 날짜입니다."
                        exception.errorType shouldBe ErrorType.INVALID_DATE
                    }
                }
            }
        }

        Context("내 식물 전체 조회") {
            every { plantRepsitory.findAll() } returns
                listOf(
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        Alarm(
                            waterAlarm = true,
                            waterPeriod = 5,
                            nutrientsAlarm = false,
                            nutrientsPeriod = 30,
                            repotAlarm = true,
                            repotPeriod = 60,
                        ),
                    ),
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME2,
                        nickname = NICKNAME2,
                        startDate = START_DATE2,
                        lastWateredDate = LAST_WATERED_DATE2,
                        Alarm(
                            waterAlarm = true,
                            waterPeriod = 5,
                            nutrientsAlarm = false,
                            nutrientsPeriod = 30,
                            repotAlarm = true,
                            repotPeriod = 60,
                        ),
                    ),
                )
            Given("내 식물을 전체 조회할 때") {
                When("조회하면") {
                    val response = myPlantService.findAllPlant()
                    Then("내 식물 리스트가 조회되어야 한다.") {
                        response.size shouldBe 2
                        response[0].nickname shouldBe NICKNAME
                        response[0].scientificName shouldBe SCIENTIFIC_NAME
                        response[1].nickname shouldBe NICKNAME2
                        response[1].scientificName shouldBe SCIENTIFIC_NAME2
                    }
                }
            }
        }

        Context("내 식물 상세 조회") {
            every { plantRepsitory.findByIdOrNull(ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    Alarm(
                        waterAlarm = true,
                        waterPeriod = 5,
                        nutrientsAlarm = false,
                        nutrientsPeriod = 30,
                        repotAlarm = true,
                        repotPeriod = 60,
                    ),
                )
            every { plantRepsitory.findByIdOrNull(not(eq(ID))) } returns
                null
            Given("존재하는 ID로") {
                When("상세 조회하면") {
                    val response = myPlantService.findPlantDetail(ID)
                    Then("내 식물의 상세 정보가 조회되어야 한다.") {
                        response.nickname shouldBe NICKNAME
                        response.scientificName shouldBe SCIENTIFIC_NAME
                        response.startDate shouldBe START_DATE
                        response.lastWatedDate shouldBe LAST_WATERED_DATE
                    }
                }
            }
            Given("존재하지 않는 ID로") {
                When("상세 조회하면") {
                    Then("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<NotFoundException> {
                                myPlantService.findPlantDetail(ID2)
                            }
                        exception.message shouldBe "존재하지 않는 내 식물입니다."
                        exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                    }
                }
            }
        }
    },
) {
    companion object {
        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)

        const val ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"
        val START_DATE2: LocalDate = LocalDate.of(2024, 3, 20)
        val LAST_WATERED_DATE2: LocalDate = LocalDate.of(2024, 7, 20)

        val FUTURE_DATE: LocalDate = LocalDate.of(5000, 5, 17)
    }
}
