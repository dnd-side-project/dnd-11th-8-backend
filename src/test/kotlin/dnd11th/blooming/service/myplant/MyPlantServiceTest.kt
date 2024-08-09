package dnd11th.blooming.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantManageRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.service.myplant.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class MyPlantServiceTest : DescribeSpec(
    {
        val myPlantRepsitory = mockk<MyPlantRepository>()
        val locationRepository = mockk<LocationRepository>()
        val myPlantService = MyPlantService(myPlantRepsitory, locationRepository)

        describe("내 식물 저장") {
            every { myPlantRepsitory.save(any()) } returns
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
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("정상적으로 저장되고 예외가 발생하면 안된다.") {
                    val result = myPlantService.saveMyPlant(request, CURRENT_DAY)

                    result.myPlantId shouldBe PLANT_ID
                    result.message shouldBe "등록 되었습니다."
                }
            }
            context("시작날짜가 미래인 요청으로 내 식물을 저장하면") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = FUTURE_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("InvalidDateException 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<InvalidDateException> {
                            myPlantService.saveMyPlant(request, CURRENT_DAY)
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
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("InvalidDateException 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<InvalidDateException> {
                            myPlantService.saveMyPlant(request, CURRENT_DAY)
                        }
                    exception.message shouldBe "올바르지 않은 날짜입니다."
                    exception.errorType shouldBe ErrorType.INVALID_DATE
                }
            }
            context("마지막으로 비료 준 날짜가 미래인 요청으로 내 식물을 저장하면") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = FUTURE_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("InvalidDateException 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<InvalidDateException> {
                            myPlantService.saveMyPlant(request, CURRENT_DAY)
                        }
                    exception.message shouldBe "올바르지 않은 날짜입니다."
                    exception.errorType shouldBe ErrorType.INVALID_DATE
                }
            }
        }

        describe("내 식물 전체 조회") {
            every { myPlantRepsitory.findAll() } returns
                listOf(
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                        alarm = ALARM,
                    ).apply {
                        id = PLANT_ID
                    },
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME2,
                        nickname = NICKNAME2,
                        startDate = START_DATE2,
                        lastWateredDate = LAST_WATERED_DATE2,
                        lastFertilizerDate = LAST_FERTILIZER_DATE2,
                        alarm = ALARM,
                    ).apply {
                        id = PLANT_ID2
                    },
                )
            context("내 식물을 전체 조회하면") {
                it("내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY)
                    response.size shouldBe 2
                    response[0].myPlantId shouldBe PLANT_ID
                    response[0].nickname shouldBe NICKNAME
                    response[0].scientificName shouldBe SCIENTIFIC_NAME
                    response[0].waterRemainDay shouldBe 2
                    response[0].fertilizerRemainDay shouldBe 29
                    response[1].myPlantId shouldBe PLANT_ID2
                    response[1].nickname shouldBe NICKNAME2
                    response[1].scientificName shouldBe SCIENTIFIC_NAME2
                    response[1].waterRemainDay shouldBe 2
                    response[1].fertilizerRemainDay shouldBe 29
                }
            }
        }

        describe("내 식물 상세 조회") {
            every { myPlantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID로 상세 조회하면") {
                it("내 식물의 상세 정보가 조회되어야 한다.") {
                    val response = myPlantService.findMyPlantDetail(PLANT_ID)
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
                            myPlantService.findMyPlantDetail(PLANT_ID2)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }

        describe("내 식물 수정") {
            every { myPlantRepsitory.findByIdOrNull(any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    alarm = ALARM,
                )
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            every { locationRepository.findByName(any()) } returns
                Location(
                    name = LOCATION_NAME,
                )
            every { locationRepository.findByName(not(eq(LOCATION_NAME))) } returns
                null
            context("정상 요청으로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        location = LOCATION_NAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                    )
                it("정상 흐름을 반환해야 한다.") {
                    myPlantService.modifyMyPlant(PLANT_ID, request)
                }
            }
            context("존재하지 않는 내 식물 ID로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        location = LOCATION_NAME,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> { myPlantService.modifyMyPlant(PLANT_ID2, request) }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
            context("존재하지 않는 장소 이름으로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        location = LOCATION_NAME2,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                    )
                it("NotFoundException(NOT_FOUND_LOCATION_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> { myPlantService.modifyMyPlant(PLANT_ID, request) }
                    exception.message shouldBe "존재하지 않는 위치입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_LOCATION_ID
                }
            }
        }

        describe("내 식물 삭제") {
            every { myPlantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            every { myPlantRepsitory.delete(any()) } just runs

            context("정상 요청으로 삭제하면") {
                it("정상 흐름이 반환되어야 한다.") {
                    myPlantService.deleteMyPlant(PLANT_ID)
                }
            }
            context("존재하지 않는 내 식물 ID로 삭제하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> { myPlantService.deleteMyPlant(PLANT_ID2) }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }

        describe("내 식물 관리") {
            every { myPlantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 내 식물 ID로 내 식물 관리 요청하면") {
                val request =
                    MyPlantManageRequest(
                        doWater = true,
                        doFertilizer = true,
                    )
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.manageMyPlant(PLANT_ID, request, CURRENT_DAY)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 관리 요청하면") {
                val request =
                    MyPlantManageRequest(
                        doWater = true,
                        doFertilizer = true,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.manageMyPlant(PLANT_ID2, request, CURRENT_DAY)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }

        describe("알림 변경") {
            every { myPlantRepsitory.findByIdOrNull(PLANT_ID) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    alarm = ALARM,
                ).apply {
                    id = PLANT_ID
                }
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID와 요청으로 알림 변경 요청을 하면") {
                val request =
                    AlarmModifyRequest(
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("알림 정보가 변경되어야 한다.") {
                    myPlantService.modifyMyPlantAlarm(PLANT_ID, request)
                }
            }
            context("존재하지 않는 ID와 요청으로 알림 변경 요청을 하면") {
                val request =
                    AlarmModifyRequest(
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.modifyMyPlantAlarm(PLANT_ID2, request)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT_ID
                }
            }
        }
    },
) {
    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.of(2024, 5, 17)
        val FUTURE_DATE: LocalDate = CURRENT_DAY.plusDays(1)

        const val PLANT_ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_NAME = "거실"
        val START_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_WATERED_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_FERTILIZER_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        const val PLANT_ID2 = 2L

        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"
        const val LOCATION_NAME2 = "베란다"
        val START_DATE2: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_WATERED_DATE2: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_FERTILIZER_DATE2: LocalDate = CURRENT_DAY.minusDays(1)

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = true
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true

        val ALARM: Alarm =
            Alarm(WATER_ALARM, WATER_PERIOD, FERTILIZER_ALARM, FERTILIZER_PERIOD, HEALTHCHECK_ALARM)
    }
}
