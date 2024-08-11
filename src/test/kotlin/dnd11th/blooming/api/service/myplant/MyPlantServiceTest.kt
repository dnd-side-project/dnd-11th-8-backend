package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantHealthCheckRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.ImageRepository
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
        val imageRepository = mockk<ImageRepository>()
        val myPlantMessageFactory = mockk<MyPlantMessageFactory>()
        val myPlantService =
            MyPlantService(myPlantRepsitory, locationRepository, myPlantMessageFactory, imageRepository)

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
            every { locationRepository.findByIdOrNull(any()) } returns
                LOCATION1
            context("정상 요청으로 내 식물을 저장하면") {
                val request =
                    MyPlantSaveRequest(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        locationId = LOCATION_ID,
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
                        locationId = LOCATION_ID,
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
                        locationId = LOCATION_ID,
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
                        locationId = LOCATION_ID,
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
            val myPlant1 =
                MyPlant(
                    scientificName = "병아리눈물",
                    nickname = "생성1등 물주기2등",
                    currentDate = LocalDate.of(2024, 5, 15),
                    lastWateredDate = LocalDate.of(2024, 5, 16),
                    lastFertilizerDate = LocalDate.of(2024, 5, 16),
                    alarm = ALARM,
                ).apply {
                    id = 1
                    location = LOCATION1
                }
            val myPlant2 =
                MyPlant(
                    scientificName = "몬스테라 델리오사",
                    nickname = "생성2등 물주기3등",
                    currentDate = LocalDate.of(2024, 5, 16),
                    lastWateredDate = LocalDate.of(2024, 5, 17),
                    lastFertilizerDate = LocalDate.of(2024, 5, 17),
                    alarm = ALARM,
                ).apply {
                    id = 2
                    location = LOCATION1
                }
            val myPlant3 =
                MyPlant(
                    scientificName = "선인장",
                    nickname = "생성3등 물주기1등",
                    currentDate = LocalDate.of(2024, 5, 17),
                    lastWateredDate = LocalDate.of(2024, 5, 15),
                    lastFertilizerDate = LocalDate.of(2024, 5, 15),
                    alarm = ALARM,
                ).apply {
                    id = 3
                    location = LOCATION2
                }
            every { myPlantRepsitory.findAllByLocationOrderByCreatedDateDesc(any()) } returns
                listOf(myPlant1, myPlant2, myPlant3)
            every { myPlantRepsitory.findAllByLocationOrderByCreatedDateAsc(any()) } returns
                listOf(myPlant3, myPlant2, myPlant1)
            every { myPlantRepsitory.findAllByLocationOrderByLastWateredDateDesc(any()) } returns
                listOf(myPlant3, myPlant1, myPlant2)
            every { myPlantRepsitory.findAllByLocationOrderByLastWateredDateAsc(any()) } returns
                listOf(myPlant2, myPlant1, myPlant3)
            every { locationRepository.findByIdOrNull(any()) } returns
                null
            every { locationRepository.findByIdOrNull(LOCATION_ID) } returns
                LOCATION1
            every { myPlantRepsitory.findAllByLocationOrderByCreatedDateDesc(LOCATION1) } returns
                listOf(myPlant1, myPlant2)
            context("내 식물을 최근 등록순으로 전체 조회하면") {
                it("내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY)
                    response.size shouldBe 3

                    response[0].myPlantId shouldBe 1
                    response[0].nickname shouldBe "생성1등 물주기2등"
                    response[0].scientificName shouldBe "병아리눈물"
                    response[0].waterRemainDay shouldBe 2
                    response[0].fertilizerRemainDay shouldBe 29

                    response[1].myPlantId shouldBe 2
                    response[1].nickname shouldBe "생성2등 물주기3등"
                    response[1].scientificName shouldBe "몬스테라 델리오사"
                    response[1].waterRemainDay shouldBe 3
                    response[1].fertilizerRemainDay shouldBe 30

                    response[2].myPlantId shouldBe 3
                    response[2].nickname shouldBe "생성3등 물주기1등"
                    response[2].scientificName shouldBe "선인장"
                    response[2].waterRemainDay shouldBe 1
                    response[2].fertilizerRemainDay shouldBe 28
                }
            }
            context("내 식물을 최근 등록순으로 전체 조회하면") {
                it("순서에 맞게 내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY, null, MyPlantQueryCreteria.CreatedDesc)
                    response.size shouldBe 3
                    response[0].nickname shouldBe "생성1등 물주기2등"
                    response[1].nickname shouldBe "생성2등 물주기3등"
                    response[2].nickname shouldBe "생성3등 물주기1등"
                }
            }
            context("내 식물을 오래된 등록순으로 전체 조회하면") {
                it("순서에 맞게 내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY, null, MyPlantQueryCreteria.CreatedAsc)
                    response.size shouldBe 3
                    response[0].nickname shouldBe "생성3등 물주기1등"
                    response[1].nickname shouldBe "생성2등 물주기3등"
                    response[2].nickname shouldBe "생성1등 물주기2등"
                }
            }
            context("내 식물을 최근 물 준 순으로 전체 조회하면") {
                it("순서에 맞게 내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY, null, MyPlantQueryCreteria.WateredDesc)
                    response.size shouldBe 3
                    response[0].nickname shouldBe "생성3등 물주기1등"
                    response[1].nickname shouldBe "생성1등 물주기2등"
                    response[2].nickname shouldBe "생성2등 물주기3등"
                }
            }
            context("내 식물을 오래된 물 준 순으로 전체 조회하면") {
                it("순서에 맞게 내 식물 리스트가 조회되어야 한다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY, null, MyPlantQueryCreteria.WateredAsc)
                    response.size shouldBe 3
                    response[0].nickname shouldBe "생성2등 물주기3등"
                    response[1].nickname shouldBe "생성1등 물주기2등"
                    response[2].nickname shouldBe "생성3등 물주기1등"
                }
            }
            context("내 식물을 locationId를 포함하여 전체 조회하면") {
                it("해당 location의 식물만이 조회된다.") {
                    val response = myPlantService.findAllMyPlant(CURRENT_DAY, LOCATION_ID)
                    response.size shouldBe 2
                    response[0].nickname shouldBe "생성1등 물주기2등"
                    response[1].nickname shouldBe "생성2등 물주기3등"
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
            every { imageRepository.findAllByMyPlant(any()) } returns
                listOf(
                    Image(
                        url = "url1",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ),
                    Image(
                        url = "url2",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ),
                )

            every { myPlantMessageFactory.createWateredTitle() } returns
                WATERED_TITLE
            every { myPlantMessageFactory.createWateredInfo(any(), any()) } returns
                WATERED_INFO
            every { myPlantMessageFactory.createFertilizerTitle() } returns
                FERTILIZER_TITLE
            every { myPlantMessageFactory.createFertilizerInfo(any(), any()) } returns
                FERTILIZER_INFO
            every { myPlantRepsitory.findByIdOrNull(not(eq(PLANT_ID))) } returns
                null
            context("존재하는 ID로 상세 조회하면") {
                it("내 식물의 상세 정보가 조회되어야 한다.") {
                    val response = myPlantService.findMyPlantDetail(PLANT_ID, CURRENT_DAY)
                    response.nickname shouldBe NICKNAME
                    response.scientificName shouldBe SCIENTIFIC_NAME
                    response.startDate shouldBe START_DATE
                    response.lastWateredTitle shouldBe WATERED_TITLE
                    response.lastWateredInfo shouldBe WATERED_INFO
                    response.lastFertilizerTitle shouldBe FERTILIZER_TITLE
                    response.lastFertilizerInfo shouldBe FERTILIZER_INFO
                    response.images.size shouldBe 2
                    response.images[0].imageUrl shouldBe "url1"
                    response.images[1].imageUrl shouldBe "url2"
                }
            }
            context("존재하지 않는 ID로 상세 조회하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.findMyPlantDetail(PLANT_ID2, CURRENT_DAY)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
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
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
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
                    exception.errorType shouldBe ErrorType.NOT_FOUND_LOCATION
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
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 물주기") {
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
            context("존재하는 내 식물 ID로 내 식물 물주기 요청하면") {
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.waterMyPlant(PLANT_ID, CURRENT_DAY)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 물주기 요청하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.waterMyPlant(PLANT_ID2, CURRENT_DAY)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 비료주기") {
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
            context("존재하는 내 식물 ID로 내 식물 비료주기 요청하면") {
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.fertilizerMyPlant(PLANT_ID, CURRENT_DAY)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 비료주기 요청하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.fertilizerMyPlant(PLANT_ID2, CURRENT_DAY)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 건강확인 알림 변경") {
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
            context("존재하는 내 식물 ID로 내 식물 비료주기 요청하면") {
                val request =
                    MyPlantHealthCheckRequest(
                        healthCheck = true,
                    )
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.modifyMyPlantHealthCheck(PLANT_ID, request)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 비료주기 요청하면") {
                val request =
                    MyPlantHealthCheckRequest(
                        healthCheck = true,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.modifyMyPlantHealthCheck(PLANT_ID2, request)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
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
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }
    },
) {
    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.of(2024, 5, 17)
        val FUTURE_DATE: LocalDate = CURRENT_DAY.plusDays(1)

        const val PLANT_ID = 1L
        const val LOCATION_ID = 100L
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

        val LOCATION1: Location =
            Location(LOCATION_NAME)

        val LOCATION2: Location =
            Location(LOCATION_NAME2)

        val FERTILIZER_TITLE = "비료주기"
        val FERTILIZER_INFO = "이번 달"
        val WATERED_TITLE = "마지막으로 물 준 날"
        val WATERED_INFO = "$LAST_WATERED_DATE\n1일전"
    }
}
