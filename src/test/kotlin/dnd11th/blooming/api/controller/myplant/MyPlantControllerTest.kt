package dnd11th.blooming.api.controller.myplant

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.myplant.AlarmModifyRequest
import dnd11th.blooming.api.dto.myplant.AlarmResponse
import dnd11th.blooming.api.dto.myplant.MyPlantDetailResponse
import dnd11th.blooming.api.dto.myplant.MyPlantResponse
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.service.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
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
                every { myPlantService.savePlant(any(), CURRENT_DAY) } just runs
                every {
                    myPlantService.savePlant(
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
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
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
                    }.andDo { print() }
                }
            }
            context("시작날짜가 미래인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            nickname = NICKNAME,
                            startDate = FUTURE_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
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
                        MockMvcResultMatchers.jsonPath("$.message").value("올바르지 않은 날짜입니다.")
                        MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.INVALID_DATE)
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜가 미래인 요청으로 저장하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            nickname = NICKNAME,
                            startDate = START_DATE,
                            lastWateredDate = FUTURE_DATE,
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
                        MockMvcResultMatchers.jsonPath("$.message").value("올바르지 않은 날짜입니다.")
                        MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.INVALID_DATE)
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 전체 조회") {
            every { myPlantService.findAllPlant() } returns
                listOf(
                    MyPlantResponse(
                        myPlantId = ID,
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                    ),
                    MyPlantResponse(
                        myPlantId = ID2,
                        nickname = NICKNAME2,
                        scientificName = SCIENTIFIC_NAME2,
                    ),
                )
            context("내 모든 식물 조회를 하면") {
                it("정상적으로 모두 조회되어야 한다.") {
                    mockMvc.get("/api/v1/plants")
                        .andExpectAll {
                            status { isOk() }
                            MockMvcResultMatchers.jsonPath("$.size()").value(2)
                            MockMvcResultMatchers.jsonPath("$[0].myPlantId").value(ID)
                            MockMvcResultMatchers.jsonPath("$[0].name").value(NICKNAME)
                            MockMvcResultMatchers.jsonPath("$[0].scientificName").value(SCIENTIFIC_NAME)
                            MockMvcResultMatchers.jsonPath("$[1].myPlantId").value(ID2)
                            MockMvcResultMatchers.jsonPath("$[1].name").value(NICKNAME2)
                            MockMvcResultMatchers.jsonPath("$[1].scientificName").value(SCIENTIFIC_NAME2)
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물 상세 조회") {
            beforeTest {
                every { myPlantService.findPlantDetail(ID) } returns
                    MyPlantDetailResponse(
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                        startDate = START_DATE,
                        lastWatedDate = LAST_WATERED_DATE,
                    )
                every { myPlantService.findPlantDetail(ID2) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)
            }
            context("존재하는 ID로 조회하면") {
                it("내 식물이 조회되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID")
                        .andExpectAll {
                            status { isOk() }
                            MockMvcResultMatchers.jsonPath("$.name").value(NICKNAME)
                            MockMvcResultMatchers.jsonPath("$.scientificName").value(SCIENTIFIC_NAME)
                            MockMvcResultMatchers.jsonPath("$.startDate").value(START_DATE)
                            MockMvcResultMatchers.jsonPath("$.lastWatedDate").value(LAST_WATERED_DATE)
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 ID로 조회하면") {
                it("예외응답이 반한되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID2")
                        .andExpectAll {
                            status { isNotFound() }
                            MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 내 식물입니다.")
                            MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_MYPLANT_ID)
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물의 알림 조회") {
            beforeTest {
                every { myPlantService.findPlantAlarm(ID) } returns
                    AlarmResponse(
                        waterAlarm = WATER_ALARM,
                        waterPeriod = WATER_PERIOD,
                        fertilizerAlarm = FERTILIZER_ALARM,
                        fertilizerPeriod = FERTILIZER_PERIOD,
                        healthCheckAlarm = HEALTHCHECK_ALARM,
                    )
                every { myPlantService.findPlantAlarm(not(eq(ID))) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)
            }
            context("존재하는 ID로 조회하면") {
                it("내 알림이 조회되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID/alarm")
                        .andExpectAll {
                            status { isOk() }
                            MockMvcResultMatchers.jsonPath("$.waterAlarm").value(WATER_ALARM)
                            MockMvcResultMatchers.jsonPath("$.waterPeriod").value(WATER_PERIOD)
                            MockMvcResultMatchers.jsonPath("$.fetilizerAlarm").value(FERTILIZER_ALARM)
                            MockMvcResultMatchers.jsonPath("$.fetilizerPeriod").value(FERTILIZER_PERIOD)
                            MockMvcResultMatchers.jsonPath("$.healthcheckAlarm").value(HEALTHCHECK_ALARM)
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 ID로 조회하면") {
                it("예외응답이 반환되어야 한다.") {
                    mockMvc.get("/api/v1/plants/$ID2/alarm")
                        .andExpectAll {
                            status { isNotFound() }
                            MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 내 식물입니다.")
                            MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_MYPLANT_ID)
                        }.andDo { print() }
                }
            }
        }

        describe("내 식물의 알림 수정") {
            beforeTest {
                every { myPlantService.modifyPlantAlarm(ID, any()) } just runs
                every { myPlantService.modifyPlantAlarm(not(eq(ID)), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_MYPLANT_ID)
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
                            MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 내 식물입니다.")
                            MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_MYPLANT_ID)
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
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)

        const val ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NICKNAME2 = "빵빵이"

        val FUTURE_DATE: LocalDate = LocalDate.of(5000, 5, 17)

        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = false
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true
    }
}
