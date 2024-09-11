package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantCreateDto
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantWithImageUrl
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
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
import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.PlantRepository
import dnd11th.blooming.domain.repository.myplant.MyPlantRepository
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
        val plantRepository = mockk<PlantRepository>()
        val locationRepository = mockk<LocationRepository>()
        val imageRepository = mockk<ImageRepository>()
        val myPlantMessageFactory = mockk<MyPlantMessageFactory>()
        val myPlantService =
            MyPlantService(
                myPlantRepsitory,
                plantRepository,
                locationRepository,
                myPlantMessageFactory,
                imageRepository,
            )

        describe("내 식물 저장") {
            every { myPlantMessageFactory.createSaveMessage() } returns
                "등록 되었습니다."
            every { plantRepository.findByIdOrNull(PLANT_ID) } returns
                PLANT
            every { myPlantRepsitory.save(any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { locationRepository.findByIdAndUser(any(), any()) } returns
                LOCATION1
            context("정상 요청으로 내 식물을 저장하면") {
                val request =
                    MyPlantCreateDto(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        plantId = PLANT_ID,
                        locationId = LOCATION_ID,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE_INT,
                        lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                it("정상적으로 저장되고 예외가 발생하면 안된다.") {
                    val result = myPlantService.saveMyPlant(request, USER, CURRENT_DAY)

                    result.myPlantId shouldBe MYPLANT_ID
                    result.message shouldBe "등록 되었습니다."
                }
            }
        }

        describe("내 식물 전체 조회") {
            val myPlant1 =
                MyPlant(
                    scientificName = "병아리눈물",
                    nickname = "식물1",
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = 1
                    location = LOCATION1
                }
            val myPlant2 =
                MyPlant(
                    scientificName = "몬스테라 델리오사",
                    nickname = "식물2",
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = 2
                    location = LOCATION1
                }
            val myPlant3 =
                MyPlant(
                    scientificName = "선인장",
                    nickname = "식물3",
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = 3
                    location = null
                }
            every { myPlantRepsitory.findAllByUser(any()) } returns
                listOf(myPlant1, myPlant2, myPlant3)
            every { imageRepository.findMyPlantAndMostRecentFavoriteImageByUser(any()) } returns
                listOf(
                    MyPlantWithImageUrl(myPlant1, "url1"),
                    MyPlantWithImageUrl(myPlant2, "url2"),
                    MyPlantWithImageUrl(myPlant3, "url3"),
                )
            every { locationRepository.findByIdOrNull(any()) } returns
                null
            every { locationRepository.findByIdOrNull(LOCATION_ID) } returns
                LOCATION1
            context("내 식물을 전체 조회하면") {
                it("내 식물 리스트가 조회되어야 한다.") {
                    val response =
                        myPlantService.findAllMyPlant(
                            CURRENT_DAY,
                            user = USER,
                        )
                    response.size shouldBe 3

                    response[0].myPlantId shouldBe 1
                    response[0].nickname shouldBe "식물1"
                    response[0].locationId shouldBe LOCATION1.id
                    response[0].nickname shouldBe "식물1"
                    response[0].imageUrl shouldBe "url1"
                    response[0].scientificName shouldBe "병아리눈물"
                    response[0].dateSinceLastWater shouldBe 1
                    response[0].dateSinceLastFertilizer shouldBe 1
                    response[0].dateSinceLastHealthCheck shouldBe 1

                    response[1].myPlantId shouldBe 2
                    response[1].nickname shouldBe "식물2"
                    response[1].locationId shouldBe LOCATION1.id
                    response[1].imageUrl shouldBe "url2"
                    response[1].scientificName shouldBe "몬스테라 델리오사"
                    response[0].dateSinceLastWater shouldBe 1
                    response[0].dateSinceLastFertilizer shouldBe 1
                    response[0].dateSinceLastHealthCheck shouldBe 1

                    response[2].myPlantId shouldBe 3
                    response[2].nickname shouldBe "식물3"
                    response[2].locationId shouldBe null
                    response[2].imageUrl shouldBe "url3"
                    response[2].scientificName shouldBe "선인장"
                    response[0].dateSinceLastWater shouldBe 1
                    response[0].dateSinceLastFertilizer shouldBe 1
                    response[0].dateSinceLastHealthCheck shouldBe 1
                }
            }
        }

        describe("내 식물 상세 조회") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            every { imageRepository.findAllByMyPlant(any()) } returns
                listOf(
                    Image(
                        url = "url1",
                        favorite = true,
                    ),
                    Image(
                        url = "url2",
                        favorite = false,
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
            every { myPlantRepsitory.findByIdOrNull(not(eq(MYPLANT_ID))) } returns
                null
            context("존재하는 ID로 상세 조회하면") {
                it("내 식물의 상세 정보가 조회되어야 한다.") {
                    val response = myPlantService.findMyPlantDetail(MYPLANT_ID, CURRENT_DAY, USER)
                    response.nickname shouldBe NICKNAME
                    response.scientificName shouldBe SCIENTIFIC_NAME
                    response.withDays shouldBe 1
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
                            myPlantService.findMyPlantDetail(MYPLANT_ID2, CURRENT_DAY, USER)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 수정") {
            every { myPlantRepsitory.findByIdAndUser(any(), any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                )
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            every { locationRepository.findByIdAndUser(LOCATION_ID, any()) } returns
                Location(
                    name = LOCATION_NAME,
                )
            every { locationRepository.findByIdAndUser(not(eq(LOCATION_ID)), any()) } returns
                null
            context("정상 요청으로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        locationId = LOCATION_ID,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE_INT,
                        lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                    )
                it("정상 흐름을 반환해야 한다.") {
                    myPlantService.modifyMyPlant(MYPLANT_ID, request, USER, CURRENT_DAY)
                }
            }
            context("존재하지 않는 내 식물 ID로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        locationId = LOCATION_ID,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE_INT,
                        lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                    )
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.modifyMyPlant(
                                MYPLANT_ID2,
                                request,
                                USER,
                                CURRENT_DAY,
                            )
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
            context("존재하지 않는 장소 ID로 수정하면") {
                val request =
                    MyPlantModifyRequest(
                        nickname = NICKNAME,
                        locationId = LOCATION_ID + 1,
                        startDate = START_DATE,
                        lastWateredDate = LAST_WATERED_DATE_INT,
                        lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                    )
                it("NotFoundException(NOT_FOUND_LOCATION_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.modifyMyPlant(
                                MYPLANT_ID,
                                request,
                                USER,
                                CURRENT_DAY,
                            )
                        }
                    exception.message shouldBe "존재하지 않는 위치입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_LOCATION
                }
            }
        }

        describe("내 식물 삭제") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            every { myPlantRepsitory.delete(any()) } just runs
            every { imageRepository.deleteAllInBatchByMyPlant(any()) } just runs

            context("정상 요청으로 삭제하면") {
                it("정상 흐름이 반환되어야 한다.") {
                    myPlantService.deleteMyPlant(MYPLANT_ID, USER)
                }
            }
            context("존재하지 않는 내 식물 ID로 삭제하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> { myPlantService.deleteMyPlant(MYPLANT_ID2, USER) }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 물주기") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            context("존재하는 내 식물 ID로 내 식물 물주기 요청하면") {
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.waterMyPlant(MYPLANT_ID, CURRENT_DAY, USER)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 물주기 요청하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.waterMyPlant(MYPLANT_ID2, CURRENT_DAY, USER)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 비료주기") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            context("존재하는 내 식물 ID로 내 식물 비료주기 요청하면") {
                it("정상 흐름이 반환된다.") {
                    shouldNotThrowAny {
                        myPlantService.fertilizerMyPlant(MYPLANT_ID, CURRENT_DAY, USER)
                    }
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 비료주기 요청하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.fertilizerMyPlant(MYPLANT_ID2, CURRENT_DAY, USER)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("내 식물 눈길주기") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
                null
            every { myPlantMessageFactory.createHealthCheckMessage() } returns
                "팁"
            context("존재하는 내 식물 ID로 내 식물 눈길주기 요청하면") {
                it("정상 흐름이 반환된다.") {
                    val result = myPlantService.healthCheckMyPlant(MYPLANT_ID, CURRENT_DAY, USER)

                    result.tipMessage shouldBe "팁"
                }
            }
            context("존재하지 않는 내 식물 ID로 내 식물 비료주기 요청하면") {
                it("NotFoundException(NOT_FOUND_MYPLANT_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            myPlantService.healthCheckMyPlant(MYPLANT_ID2, CURRENT_DAY, USER)
                        }
                    exception.message shouldBe "존재하지 않는 내 식물입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_MYPLANT
                }
            }
        }

        describe("알림 변경") {
            every { myPlantRepsitory.findByIdAndUser(MYPLANT_ID, any()) } returns
                MyPlant(
                    scientificName = SCIENTIFIC_NAME,
                    nickname = NICKNAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    lastFertilizerDate = LAST_FERTILIZER_DATE,
                    lastHealthCheckDate = LAST_HEALTHCHECK_DATE,
                    alarm = ALARM,
                ).apply {
                    id = MYPLANT_ID
                }
            every { myPlantRepsitory.findByIdAndUser(not(eq(MYPLANT_ID)), any()) } returns
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
                    myPlantService.modifyMyPlantAlarm(MYPLANT_ID, request.toAlarmModifyDto(), USER, START_DATE.month)
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
                            myPlantService.modifyMyPlantAlarm(MYPLANT_ID2, request.toAlarmModifyDto(), USER, START_DATE.month)
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

        const val PLANT_ID = 1L
        const val MYPLANT_ID = 1L
        const val MYPLANT_ID2 = 2L
        const val LOCATION_ID = 100L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_NAME = "거실"
        const val LOCATION_NAME2 = "베란다"

        val START_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        val LAST_WATERED_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        const val LAST_WATERED_DATE_INT = 1
        val LAST_FERTILIZER_DATE: LocalDate = CURRENT_DAY.minusDays(1)
        const val LAST_FERTILIZER_DATE_INT = 1
        val LAST_HEALTHCHECK_DATE: LocalDate = CURRENT_DAY.minusDays(1)

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = true
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true

        val USER = User("email", "nickname", AlarmTime.TIME_12_13, 100, 100, 1)

        val ALARM: Alarm =
            Alarm(WATER_ALARM, WATER_PERIOD, FERTILIZER_ALARM, FERTILIZER_PERIOD, HEALTHCHECK_ALARM)

        val PLANT: Plant =
            Plant(
                "",
                "",
                Water.MOIST,
                Water.MOIST,
                Water.MOIST,
                Water.MOIST,
                100,
                100,
                Light.MEDIUM,
                Difficulty.HIGH,
                GrowTemperature.GROW_TEMPERATURE_16_20,
                LowestTemperature.LOWEST_TEMPERATURE_13,
                Toxicity.EXISTS,
                Fertilizer.LOW_DEMAND,
                Humidity.HUMIDITY_0_40,
                "",
                GrowLocation.VERANDA,
                "",
            )

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
