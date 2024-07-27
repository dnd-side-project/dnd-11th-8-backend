package dnd11th.blooming.service

import dnd11th.blooming.api.dto.AlarmEditRequest
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
                    alarm = ALARM,
                )
            Given("정상 요청으로") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        nutrientsAlarm = NUTRIENTS_ALARM,
                        nutrientsPeriod = NUTRIENTS_PERIOD,
                        repotAlarm = REPOT_ALARM,
                        repotPeriod = REPOT_PERIDO,
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
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        nutrientsAlarm = NUTRIENTS_ALARM,
                        nutrientsPeriod = NUTRIENTS_PERIOD,
                        repotAlarm = REPOT_ALARM,
                        repotPeriod = REPOT_PERIDO,
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
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        nutrientsAlarm = NUTRIENTS_ALARM,
                        nutrientsPeriod = NUTRIENTS_PERIOD,
                        repotAlarm = REPOT_ALARM,
                        repotPeriod = REPOT_PERIDO,
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
                        alarm = ALARM,
                    ),
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME2,
                        nickname = NICKNAME2,
                        startDate = START_DATE2,
                        lastWateredDate = LAST_WATERED_DATE2,
                        alarm = ALARM,
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
                    alarm = ALARM,
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

        Context("알림 조회") {
            every { plantRepsitory.findByIdOrNull(ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                )
            every { plantRepsitory.findByIdOrNull(not(eq(ID))) } returns
                null
            Given("존재하는 ID로") {
                When("알림 조회 요청을 하면") {
                    val response = myPlantService.findPlantAlarm(ID)
                    Then("내 식물의 알림 정보가 조회되어야 한다.") {
                        response.waterAlarm shouldBe WATER_ALARM
                        response.waterPeriod shouldBe WATER_PERIOD
                        response.nutrientsAlarm shouldBe NUTRIENTS_ALARM
                        response.nutrientsPeriod shouldBe NUTRIENTS_PERIOD
                        response.repotAlarm shouldBe REPOT_ALARM
                        response.repotPeriod shouldBe REPOT_PERIDO
                    }
                }
            }
            Given("존재하지 않는 ID로") {
                When("알림 조회 요청을 하면") {
                    Then("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<NotFoundException> {
                                myPlantService.findPlantAlarm(ID2)
                            }
                        exception.message shouldBe "존재하지 않는 내 식물입니다."
                        exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                    }
                }
            }
        }

        Context("알림 변경") {
            every { plantRepsitory.findByIdOrNull(ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                )
            every { plantRepsitory.findByIdOrNull(not(eq(ID))) } returns
                null
            Given("존재하는 ID와 요청으로") {
                val request =
                    AlarmEditRequest(
                        waterAlarm = NEW_WATER_ALARM,
                        waterPeriod = NEW_WATER_PERIOD,
                        nutrientsAlarm = NEW_NUTRIENTS_ALARM,
                        nutrientsPeriod = NEW_NUTRIENTS_PERIOD,
                        repotAlarm = NEW_REPOT_ALARM,
                        repotPeriod = NEW_REPOT_PERIDO,
                    )
                When("알림 변경 요청을 하면") {
                    myPlantService.editPlantAlarm(ID, request)
                    Then("내 식물의 알림 정보가 변경되어야 한다.") {
                    }
                }
            }
            Given("존재하지 않는 ID와 요청으로") {
                val request =
                    AlarmEditRequest(
                        waterAlarm = NEW_WATER_ALARM,
                        waterPeriod = NEW_WATER_PERIOD,
                        nutrientsAlarm = NEW_NUTRIENTS_ALARM,
                        nutrientsPeriod = NEW_NUTRIENTS_PERIOD,
                        repotAlarm = NEW_REPOT_ALARM,
                        repotPeriod = NEW_REPOT_PERIDO,
                    )
                When("알림 변경 요청을 하면") {
                    Then("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                        val exception =
                            shouldThrow<NotFoundException> {
                                myPlantService.editPlantAlarm(ID2, request)
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

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val NUTRIENTS_ALARM = true
        const val NUTRIENTS_PERIOD = 30
        const val REPOT_ALARM = true
        const val REPOT_PERIDO = 60
        const val NEW_WATER_ALARM = false
        const val NEW_WATER_PERIOD = 5
        const val NEW_NUTRIENTS_ALARM = false
        const val NEW_NUTRIENTS_PERIOD = 45
        const val NEW_REPOT_ALARM = false
        const val NEW_REPOT_PERIDO = 70
        val ALARM: Alarm =
            Alarm(WATER_ALARM, WATER_PERIOD, NUTRIENTS_ALARM, NUTRIENTS_PERIOD, REPOT_ALARM, REPOT_PERIDO)
    }
}
