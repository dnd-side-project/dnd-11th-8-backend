package dnd11th.blooming.api.controller.myplant

import dnd11th.blooming.api.dto.image.ImageResponse
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.HealthCheckResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.support.WebMvcDescribeSpec
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MyPlantControllerTest : WebMvcDescribeSpec() {
    init {
        describe("내 식물 저장") {
            beforeTest {
                every { myPlantService.saveMyPlant(any(), any(), any()) } returns
                    MyPlantSaveResponse(
                        myPlantId = MYPLANT_ID,
                        message = "등록 되었습니다.",
                    )
            }
            context("정상적인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            plantId = PLANT_ID,
                            scientificName = SCIENTIFIC_NAME,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE_INT,
                            lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("정상 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isOk() }
                        jsonPath("$.myPlantId", equalTo(MYPLANT_ID.toInt()))
                        jsonPath("$.message", equalTo("등록 되었습니다."))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 전체 조회") {
            every { myPlantService.findAllMyPlant(any(), any()) } returns
                listOf(
                    MyPlantResponse(
                        myPlantId = MYPLANT_ID,
                        nickname = NICKNAME,
                        locationId = LOCATION_ID,
                        registeredDateTime = REGISTERED_DATE,
                        imageUrl = IMAGE_URL,
                        scientificName = SCIENTIFIC_NAME,
                        dateSinceLastWater = DATE_SINCE_LAST_WATER,
                        dateSinceLastFertilizer = DATE_SINCE_LAST_FERTILIZER,
                        dateSinceLastHealthCheck = DATE_SINCE_LAST_HEALTHCHECK,
                    ),
                    MyPlantResponse(
                        myPlantId = MYPLANT_ID2,
                        nickname = NICKNAME2,
                        locationId = LOCATION_ID,
                        registeredDateTime = REGISTERED_DATE,
                        imageUrl = IMAGE_URL,
                        scientificName = SCIENTIFIC_NAME2,
                        dateSinceLastWater = DATE_SINCE_LAST_WATER,
                        dateSinceLastFertilizer = DATE_SINCE_LAST_FERTILIZER,
                        dateSinceLastHealthCheck = DATE_SINCE_LAST_HEALTHCHECK,
                    ),
                )
            context("내 모든 식물 조회를 하면") {
                it("정상적으로 모두 조회되어야 한다.") {
                    mockMvc.get("/api/v1/myplants")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.size()", equalTo(2))
                            jsonPath("$[0].myPlantId", equalTo(MYPLANT_ID.toInt()))
                            jsonPath("$[0].nickname", equalTo(NICKNAME))
                            jsonPath("$[0].locationId", equalTo(LOCATION_ID.toInt()))
                            jsonPath("$[0].imageUrl", equalTo(IMAGE_URL))
                            jsonPath("$[0].scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$[0].dateSinceLastWater", equalTo(DATE_SINCE_LAST_WATER))
                            jsonPath("$[0].dateSinceLastFertilizer", equalTo(DATE_SINCE_LAST_FERTILIZER))
                            jsonPath("$[0].dateSinceLastHealthCheck", equalTo(DATE_SINCE_LAST_HEALTHCHECK))
                            jsonPath("$[1].myPlantId", equalTo(MYPLANT_ID2.toInt()))
                            jsonPath("$[1].nickname", equalTo(NICKNAME2))
                            jsonPath("$[1].locationId", equalTo(LOCATION_ID.toInt()))
                            jsonPath("$[1].imageUrl", equalTo(IMAGE_URL))
                            jsonPath("$[1].scientificName", equalTo(SCIENTIFIC_NAME2))
                            jsonPath("$[1].dateSinceLastWater", equalTo(DATE_SINCE_LAST_WATER))
                            jsonPath("$[1].dateSinceLastFertilizer", equalTo(DATE_SINCE_LAST_FERTILIZER))
                            jsonPath("$[1].dateSinceLastHealthCheck", equalTo(DATE_SINCE_LAST_HEALTHCHECK))
                        }.andDo { print() }
                }
            }
            every { myPlantService.findAllMyPlant(any(), any()) } returns
                listOf(
                    MyPlantResponse(
                        myPlantId = MYPLANT_ID,
                        nickname = NICKNAME,
                        locationId = LOCATION_ID,
                        registeredDateTime = REGISTERED_DATE,
                        imageUrl = IMAGE_URL,
                        scientificName = SCIENTIFIC_NAME,
                        dateSinceLastWater = DATE_SINCE_LAST_WATER,
                        dateSinceLastFertilizer = DATE_SINCE_LAST_FERTILIZER,
                        dateSinceLastHealthCheck = DATE_SINCE_LAST_HEALTHCHECK,
                    ),
                )
            context("locationId를 포함하여 내 모든 식물 조회를 하면") {
                it("해당 location의 식물만 조회된다.") {
                    mockMvc.get("/api/v1/myplants") {
                        param("locationId", LOCATION_ID.toString())
                    }
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.size()", equalTo(1))
                            jsonPath("$[0].myPlantId", equalTo(MYPLANT_ID.toInt()))
                            jsonPath("$[0].nickname", equalTo(NICKNAME))
                            jsonPath("$[0].scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$[0].dateSinceLastWater", equalTo(DATE_SINCE_LAST_WATER))
                            jsonPath("$[0].dateSinceLastFertilizer", equalTo(DATE_SINCE_LAST_FERTILIZER))
                            jsonPath("$[0].dateSinceLastHealthCheck", equalTo(DATE_SINCE_LAST_HEALTHCHECK))
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 상세 조회") {
            beforeTest {
                every { myPlantService.findMyPlantDetail(MYPLANT_ID, any(), any()) } returns
                    MyPlantDetailResponse(
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                        plantId = PLANT_ID,
                        location = LocationResponse(LOCATION_ID, LOCATION_NAME),
                        withDays = WITH_DAYS,
                        lastWateredTitle = LAST_WATERED_TITLE,
                        lastWateredInfo = LAST_WATERED_INFO,
                        lastWateredDate = LAST_WATERED_DATE,
                        lastFertilizerTitle = LAST_FERTILIZER_TITLE,
                        lastFertilizerInfo = LAST_FERTILIZER_INFO,
                        lastFertilizerDate = LAST_FERTILIZER_DATE,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                        images =
                            listOf(
                                ImageResponse(
                                    imageId = IMAGE_ID,
                                    imageUrl = "url1",
                                    favorite = true,
                                    createdDate = CURRENT_DAY,
                                ),
                                ImageResponse(
                                    imageId = IMAGE_ID,
                                    imageUrl = "url2",
                                    favorite = false,
                                    createdDate = CURRENT_DAY,
                                ),
                            ),
                    )
                every { myPlantService.findMyPlantDetail(MYPLANT_ID2, any(), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
            }
            context("존재하는 ID로 조회하면") {
                it("내 식물이 조회되어야 한다.") {
                    mockMvc.get("/api/v1/myplants/$MYPLANT_ID")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.nickname", equalTo(NICKNAME))
                            jsonPath("$.scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$.location.id", equalTo(LOCATION_ID.toInt()))
                            jsonPath("$.location.name", equalTo(LOCATION_NAME))
                            jsonPath("$.withDays", equalTo(WITH_DAYS))
                            jsonPath("$.lastWateredTitle", equalTo(LAST_WATERED_TITLE))
                            jsonPath("$.lastWateredInfo", equalTo(LAST_WATERED_INFO))
                            jsonPath("$.lastFertilizerTitle", equalTo(LAST_FERTILIZER_TITLE))
                            jsonPath("$.lastFertilizerInfo", equalTo(LAST_FERTILIZER_INFO))
                            jsonPath("$.waterAlarm", equalTo(WATER_ALARM))
                            jsonPath("$.waterPeriod", equalTo(WATER_PERIOD))
                            jsonPath("$.fertilizerAlarm", equalTo(FERTILIZER_ALARM))
                            jsonPath("$.fertilizerPeriod", equalTo(FERTILIZER_PERIOD))
                            jsonPath("$.healthCheckAlarm", equalTo(HEALTHCHECK_ALARM))
                            jsonPath("$.images.size()", equalTo(2))
                            jsonPath("$.images[0].imageId", equalTo(IMAGE_ID.toInt()))
                            jsonPath("$.images[0].imageUrl", equalTo("url1"))
                            jsonPath("$.images[1].imageId", equalTo(IMAGE_ID.toInt()))
                            jsonPath("$.images[1].imageUrl", equalTo("url2"))
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 ID로 조회하면") {
                it("예외응답이 반한되어야 한다.") {
                    mockMvc.get("/api/v1/myplants/$MYPLANT_ID2")
                        .andExpectAll {
                            status { isNotFound() }
                            jsonPath("$.message", equalTo("존재하지 않는 내 식물입니다."))
                            jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_MYPLANT.name))
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 수정") {
            beforeTest {
                every { myPlantService.modifyMyPlant(any(), any(), any(), any()) } just runs
                every { myPlantService.modifyMyPlant(not(eq(MYPLANT_ID)), any(), any(), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
                every {
                    myPlantService.modifyMyPlant(
                        any(),
                        match {
                            it.locationId != LOCATION_ID
                        },
                        any(),
                        any(),
                    )
                } throws
                    NotFoundException(ErrorType.NOT_FOUND_LOCATION)
            }
            context("정상 요청으로 수정하면") {
                val request =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE_INT,
                            lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                        ),
                    )
                it("정상 흐름이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                    }.andDo { print() }
                }
            }
            context("존재하지 않는 내 식물 ID로 수정하면") {
                val request =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE_INT,
                            lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID2") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isNotFound() }
                        jsonPath("$.message", equalTo("존재하지 않는 내 식물입니다."))
                        jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_MYPLANT.name))
                    }.andDo { print() }
                }
            }
            context("존재하지 않는 장소 이름로 수정하면") {
                val request =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID + 1,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE_INT,
                            lastFertilizerDate = LAST_FERTILIZER_DATE_INT,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID2") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isNotFound() }
                        jsonPath("$.message", equalTo("존재하지 않는 위치입니다."))
                        jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_LOCATION.name))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 삭제") {
            beforeTest {
                every { myPlantService.deleteMyPlant(any(), any()) } just runs
                every { myPlantService.deleteMyPlant(not(eq(MYPLANT_ID)), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
            }
            context("정상 요청으로 삭제하면") {
                it("정상 흐름이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/myplants/$MYPLANT_ID")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 내 식물 ID로 삭제하면") {
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/myplants/$MYPLANT_ID2")
                        .andExpectAll {
                            status { isNotFound() }
                            jsonPath("$.message", equalTo("존재하지 않는 내 식물입니다."))
                            jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_MYPLANT.name))
                        }
                        .andDo { print() }
                }
            }
        }

        describe("내 식물 물주기") {
            beforeTest {
                every { myPlantService.waterMyPlant(any(), any(), any()) } just runs
            }
            context("물주기 요청을 하면") {
                it("정상 흐름이 반환된다.") {
                    mockMvc.post("/api/v1/myplants/$MYPLANT_ID/water")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 비료주기") {
            beforeTest {
                every { myPlantService.fertilizerMyPlant(any(), any(), any()) } just runs
            }
            context("물주기 요청을 하면") {
                it("정상 흐름이 반환된다.") {
                    mockMvc.post("/api/v1/myplants/$MYPLANT_ID/fertilizer")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 눈길주기") {
            beforeTest {
                every { myPlantService.healthCheckMyPlant(any(), any(), any()) } returns
                    HealthCheckResponse("팁")
            }
            context("눈길주기 요청을 하면") {
                it("정상 흐름이 반환된다.") {
                    mockMvc.post("/api/v1/myplants/$MYPLANT_ID/healthcheck")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.tipMessage", equalTo("팁"))
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물의 알림 수정") {
            beforeTest {
                every { myPlantService.modifyMyPlantAlarm(MYPLANT_ID, any(), any(), any()) } just runs
                every { myPlantService.modifyMyPlantAlarm(not(eq(MYPLANT_ID)), any(), any(), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
            }
            context("존재하는 ID로 수정하면") {
                val json =
                    objectMapper.writeValueAsString(
                        AlarmModifyRequest(
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("정상응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID/alarm") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 ID로 수정하면") {
                val json =
                    objectMapper.writeValueAsString(
                        AlarmModifyRequest(
                            waterAlarm = WATER_ALARM,
                            waterPeriod = WATER_PERIOD,
                            fertilizerAlarm = FERTILIZER_ALARM,
                            fertilizerPeriod = FERTILIZER_PERIOD,
                            healthCheckAlarm = HEALTHCHECK_ALARM,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/myplants/$MYPLANT_ID2/alarm") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }
                        .andExpectAll {
                            status { isNotFound() }
                            jsonPath("$.message", equalTo("존재하지 않는 내 식물입니다."))
                            jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_MYPLANT.name))
                        }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.now()

        const val PLANT_ID = 1L
        const val IMAGE_ID = 1L
        const val IMAGE_URL = "http://"

        const val MYPLANT_ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_ID = 100L
        const val LOCATION_NAME = "거실"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val REGISTERED_DATE: LocalDateTime = LocalDateTime.of(START_DATE, LocalTime.NOON)
        const val WITH_DAYS = 234
        const val LAST_WATERED_DATE_INT = 1
        val LAST_WATERED_DATE: LocalDate = CURRENT_DAY.minusDays(LAST_WATERED_DATE_INT.toLong())
        const val LAST_FERTILIZER_DATE_INT = 2
        val LAST_FERTILIZER_DATE: LocalDate = CURRENT_DAY.minusDays(LAST_FERTILIZER_DATE_INT.toLong())

//        val LAST_HEALTHCHECK_DATE: LocalDate = LocalDate.of(2024, 6, 15)
        const val DATE_SINCE_LAST_WATER = 3
        const val DATE_SINCE_LAST_FERTILIZER = 3
        const val DATE_SINCE_LAST_HEALTHCHECK = 3

        const val MYPLANT_ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = false
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true

        const val LAST_FERTILIZER_TITLE = "비료주기"
        const val LAST_FERTILIZER_INFO = "이번 달"
        const val LAST_WATERED_TITLE = "마지막으로 물 준 날"
        const val LAST_WATERED_INFO = "$LAST_WATERED_DATE_INT\n1일전"
    }
}
