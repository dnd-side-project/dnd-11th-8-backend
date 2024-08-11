package dnd11th.blooming.api.controller.myplant

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.image.ImageResponse
import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantHealthCheckRequest
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveResponse
import dnd11th.blooming.api.service.myplant.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest(MyPlantController::class)
class MyPlantControllerTest : DescribeSpec() {
    @MockkBean
    private lateinit var myPlantService: MyPlantService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        describe("내 식물 저장") {
            beforeTest {
                every { myPlantService.saveMyPlant(any(), CURRENT_DAY) } returns
                    MyPlantSaveResponse(
                        myPlantId = ID,
                    )
                every {
                    myPlantService.saveMyPlant(
                        match {
                            it.startDate == FUTURE_DATE || it.lastWateredDate == FUTURE_DATE
                        },
                        CURRENT_DAY,
                    )
                } throws
                    InvalidDateException(ErrorType.INVALID_DATE)
            }
            context("정상적인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
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
                        ),
                    )
                it("정상 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isOk() }
                        jsonPath("$.myPlantId", equalTo(ID.toInt()))
                        jsonPath("$.message", equalTo("등록 되었습니다."))
                    }.andDo { print() }
                }
            }
            context("시작날짜가 미래인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = FUTURE_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                            waterAlarm = true,
                            waterPeriod = 60,
                            fertilizerAlarm = false,
                            fertilizerPeriod = null,
                            healthCheckAlarm = true,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.message", equalTo("올바르지 않은 날짜입니다."))
                        jsonPath("$.code", equalTo(ErrorType.INVALID_DATE.name))
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜가 미래인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
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
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.message", equalTo("올바르지 않은 날짜입니다."))
                        jsonPath("$.code", equalTo(ErrorType.INVALID_DATE.name))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 전체 조회") {
            every { myPlantService.findAllMyPlant(any(), any(), any()) } returns
                listOf(
                    MyPlantResponse(
                        myPlantId = ID,
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                        waterRemainDay = WATER_REAMIN_DAY,
                        fertilizerRemainDay = FERTILIZER_REAMIN_DAY,
                    ),
                    MyPlantResponse(
                        myPlantId = ID2,
                        nickname = NICKNAME2,
                        scientificName = SCIENTIFIC_NAME2,
                        waterRemainDay = WATER_REAMIN_DAY,
                        fertilizerRemainDay = FERTILIZER_REAMIN_DAY,
                    ),
                )
            context("내 모든 식물 조회를 하면") {
                it("정상적으로 모두 조회되어야 한다.") {
                    mockMvc.get("/api/v1/plants")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.size()", equalTo(2))
                            jsonPath("$[0].myPlantId", equalTo(ID.toInt()))
                            jsonPath("$[0].nickname", equalTo(NICKNAME))
                            jsonPath("$[0].scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$[0].waterRemainDay", equalTo(WATER_REAMIN_DAY))
                            jsonPath("$[0].fertilizerRemainDay", equalTo(FERTILIZER_REAMIN_DAY))
                            jsonPath("$[1].myPlantId", equalTo(ID2.toInt()))
                            jsonPath("$[1].nickname", equalTo(NICKNAME2))
                            jsonPath("$[1].scientificName", equalTo(SCIENTIFIC_NAME2))
                            jsonPath("$[1].waterRemainDay", equalTo(WATER_REAMIN_DAY))
                            jsonPath("$[1].fertilizerRemainDay", equalTo(FERTILIZER_REAMIN_DAY))
                        }.andDo { print() }
                }
            }
            every { myPlantService.findAllMyPlant(any(), LOCATION_ID, any()) } returns
                listOf(
                    MyPlantResponse(
                        myPlantId = ID,
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                        waterRemainDay = WATER_REAMIN_DAY,
                        fertilizerRemainDay = FERTILIZER_REAMIN_DAY,
                    ),
                )
            context("locationId를 포함하여 내 모든 식물 조회를 하면") {
                it("해당 location의 식물만 조회된다.") {
                    mockMvc.get("/api/v1/plants") {
                        param("locationId", LOCATION_ID.toString())
                    }
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.size()", equalTo(1))
                            jsonPath("$[0].myPlantId", equalTo(ID.toInt()))
                            jsonPath("$[0].nickname", equalTo(NICKNAME))
                            jsonPath("$[0].scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$[0].waterRemainDay", equalTo(WATER_REAMIN_DAY))
                            jsonPath("$[0].fertilizerRemainDay", equalTo(FERTILIZER_REAMIN_DAY))
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 상세 조회") {
            beforeTest {
                every { myPlantService.findMyPlantDetail(ID, any()) } returns
                    MyPlantDetailResponse(
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                        location = LOCATION_NAME,
                        startDate = START_DATE,
                        lastWateredTitle = LAST_WATERED_TITLE,
                        lastWateredInfo = LAST_WATERED_INFO,
                        lastFertilizerTitle = LAST_FERTILIZER_TITLE,
                        lastFertilizerInfo = LAST_FERTILIZER_INFO,
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                        images =
                            listOf(
                                ImageResponse(
                                    imageUrl = "url1",
                                    favorite = true,
                                    createdDate = CURRENT_DAY,
                                ),
                                ImageResponse(
                                    imageUrl = "url2",
                                    favorite = false,
                                    createdDate = CURRENT_DAY,
                                ),
                            ),
                    )
                every { myPlantService.findMyPlantDetail(ID2, any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
            }
            context("존재하는 ID로 조회하면") {
                it("내 식물이 조회되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.nickname", equalTo(NICKNAME))
                            jsonPath("$.scientificName", equalTo(SCIENTIFIC_NAME))
                            jsonPath("$.location", equalTo(LOCATION_NAME))
                            jsonPath("$.startDate", equalTo(START_DATE.toString()))
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
                            jsonPath("$.images[0].imageUrl", equalTo("url1"))
                            jsonPath("$.images[1].imageUrl", equalTo("url2"))
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 ID로 조회하면") {
                it("예외응답이 반한되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID2")
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
                every { myPlantService.modifyMyPlant(any(), any()) } just runs
                every { myPlantService.modifyMyPlant(not(eq(ID)), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
                every {
                    myPlantService.modifyMyPlant(
                        any(),
                        match {
                            it.locationId != LOCATION_ID
                        },
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
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("정상 흐름이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/plants/$ID") {
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
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/plants/$ID2") {
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
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/plants/$ID2") {
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
                every { myPlantService.deleteMyPlant(any()) } just runs
                every { myPlantService.deleteMyPlant(not(eq(ID))) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT)
            }
            context("정상 요청으로 삭제하면") {
                it("정상 흐름이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/plants/$ID")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 내 식물 ID로 삭제하면") {
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/plants/$ID2")
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
                every { myPlantService.waterMyPlant(any(), any()) } just runs
            }
            context("물주기 요청을 하면") {
                it("정상 흐름이 반환된다.") {
                    mockMvc.post("/api/v1/plants/$ID/water")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 비료주기") {
            beforeTest {
                every { myPlantService.fertilizerMyPlant(any(), any()) } just runs
            }
            context("물주기 요청을 하면") {
                it("정상 흐름이 반환된다.") {
                    mockMvc.post("/api/v1/plants/$ID/fertilizer")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 건강확인 알림 변경") {
            beforeTest {
                every { myPlantService.modifyMyPlantHealthCheck(any(), any()) } just runs
            }
            context("건강확인 알림을 변경하면") {
                val request =
                    objectMapper.writeValueAsString(
                        MyPlantHealthCheckRequest(
                            healthCheck = true,
                        ),
                    )
                it("정상 흐름이 반환된다.") {
                    mockMvc.patch("/api/v1/plants/$ID/healthcheck") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물의 알림 수정") {
            beforeTest {
                every { myPlantService.modifyMyPlantAlarm(ID, any()) } just runs
                every { myPlantService.modifyMyPlantAlarm(not(eq(ID)), any()) } throws
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
                    mockMvc.patch("/api/v1/plants/$ID/alarm") {
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
                    mockMvc.patch("/api/v1/plants/$ID2/alarm") {
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

        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_ID = 100L
        const val LOCATION_NAME = "거실"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)
        val LAST_FERTILIZER_DATE: LocalDate = LocalDate.of(2024, 6, 15)
        const val WATER_REAMIN_DAY = 3
        const val FERTILIZER_REAMIN_DAY = 3

        const val ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"

        val FUTURE_DATE: LocalDate = LocalDate.of(5000, 5, 17)

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = false
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true

        val LAST_FERTILIZER_TITLE = "비료주기"
        val LAST_FERTILIZER_INFO = "이번 달"
        val LAST_WATERED_TITLE = "마지막으로 물 준 날"
        val LAST_WATERED_INFO = "${LAST_WATERED_DATE}\n1일전"
    }
}
