package dnd11th.blooming.service

import dnd11th.blooming.api.dto.AlarmModifyRequest
import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.service.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.MyPlantRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class PlantServiceTest : DescribeSpec(
    {
        val plantRepsitory = mockk<MyPlantRepository>()
        val myPlantService = MyPlantService(plantRepsitory)

        describe("내 식물 저장") {
            every { plantRepsitory.save(any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            context("정상 요청으로 내 식물을 저장하면") {
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
                it("저장되고, 저장되었다는 응답이 와야 한다.") {
                    val response = myPlantService.savePlant(request, CURRENT_DAY)
                    response.myPlantId shouldBe PLANT_ID
                }
            }
            context("시작날짜가 미래인 요청으로 내 식물을 저장하면") {
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
                it("InvalidDateException 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<InvalidDateException> {
                            myPlantService.savePlant(request, CURRENT_DAY)
                        }
                    exception.message shouldBe "올바르지 않은 날짜입니다."
                    exception.errorType shouldBe ErrorType.INVALID_DATE
                }
            }
            context("마지막으로 물 준 날짜가 미래인 요청으로 내 식물을 저장하면") {
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
                it("InvalidDateException 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<InvalidDateException> {
                            myPlantService.savePlant(request, CURRENT_DAY)
                        }
                    exception.message shouldBe "올바르지 않은 날짜입니다."
                    exception.errorType shouldBe ErrorType.INVALID_DATE
                }
            }
        }

        describe("내 식물 전체 조회") {
            every { plantRepsitory.findAll() } returns
                listOf(
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        alarm = ALARM,
                    ).apply {
                        id = PLANT_ID
                    },
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME2,
                        nickname = NICKNAME2,
                        startDate = START_DATE2,
                        lastWateredDate = LAST_WATERED_DATE2,
                        alarm = ALARM,
                    ).apply {
                        id = PLANT_ID2
                    },
                )
            context("내 식물을 전체 조회하면") {
                it("내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllPlant()
                    response.size shouldBe 2
                    response[0].myPlantId shouldBe PLANT_ID
                    response[0].nickname shouldBe NICKNAME
                    response[0].scientificName shouldBe SCIENTIFIC_NAME
                    response[1].myPlantId shouldBe PLANT_ID2
                    response[1].nickname shouldBe NICKNAME2
                    response[1].scientificName shouldBe SCIENTIFIC_NAME2
                }
            }
        }

        describe("내 식물 상세 조회") {
            every { plantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { plantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID로 상세 조회하면") {
                it("내 식물의 상세 정보가 조회되어야 한다.") {
                    val response = myPlantService.findPlantDetail(PLANT_ID)
                    response.nickname shouldBe NICKNAME
                    response.scientificName shouldBe SCIENTIFIC_NAME
                    response.startDate shouldBe START_DATE
                    response.lastWatedDate shouldBe LAST_WATERED_DATE
                }
            }
            context("존재하지 않는 ID로 상세 조회하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.findPlantDetail(PLANT_ID2)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }

        describe("알림 조회") {
            every { plantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { plantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID로 알림 조회 요청을 하면") {
                it("내 식물의 알림 정보가 조회되어야 한다.") {
                    val response = myPlantService.findPlantAlarm(PLANT_ID)
                    response.waterAlarm shouldBe WATER_ALARM
                    response.waterPeriod shouldBe WATER_PERIOD
                    response.nutrientsAlarm shouldBe NUTRIENTS_ALARM
                    response.nutrientsPeriod shouldBe NUTRIENTS_PERIOD
                    response.repotAlarm shouldBe REPOT_ALARM
                    response.repotPeriod shouldBe REPOT_PERIDO
                }
            }
            context("존재하지 않는 ID로") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.findPlantAlarm(PLANT_ID2)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }

        describe("알림 변경") {
            every { plantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { plantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID와 요청으로 알림 변경 요청을 하면") {
                val request =
                    AlarmModifyRequest(
                        waterAlarm = NEW_WATER_ALARM,
                        waterPeriod = NEW_WATER_PERIOD,
                        nutrientsAlarm = NEW_NUTRIENTS_ALARM,
                        nutrientsPeriod = NEW_NUTRIENTS_PERIOD,
                        repotAlarm = NEW_REPOT_ALARM,
                        repotPeriod = NEW_REPOT_PERIDO,
                    )
                it("알림 정보가 변경되어야 한다.") {
                    myPlantService.modifyPlantAlarm(PLANT_ID, request)
                }
            }
            context("존재하지 않는 ID와 요청으로 알림 변경 요청을 하면") {
                val request =
                    AlarmModifyRequest(
                        waterAlarm = NEW_WATER_ALARM,
                        waterPeriod = NEW_WATER_PERIOD,
                        nutrientsAlarm = NEW_NUTRIENTS_ALARM,
                        nutrientsPeriod = NEW_NUTRIENTS_PERIOD,
                        repotAlarm = NEW_REPOT_ALARM,
                        repotPeriod = NEW_REPOT_PERIDO,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.modifyPlantAlarm(PLANT_ID2, request)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }
    },
) {
    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.now()
        val FUTURE_DATE: LocalDate = CURRENT_DAY.plusDays(1)

        const val PLANT_ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        val START_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_WATERED_DATE: LocalDate = CURRENT_DAY.minusDays(1)

        const val PLANT_ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"
        val START_DATE2: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_WATERED_DATE2: LocalDate = CURRENT_DAY.minusDays(1)

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
