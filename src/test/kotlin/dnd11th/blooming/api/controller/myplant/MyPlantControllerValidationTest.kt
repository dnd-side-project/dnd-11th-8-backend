package dnd11th.blooming.api.controller.myplant

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.myplant.MyPlantModifyRequest
import dnd11th.blooming.api.dto.myplant.MyPlantSaveRequest
import dnd11th.blooming.api.service.myplant.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@WebMvcTest(MyPlantController::class)
class MyPlantControllerValidationTest : DescribeSpec() {
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
            context("식물 별명을 비우고 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            plantId = PLANT_ID,
                            nickname = " ",
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
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("식물 별명은 비어있을 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("키우기 시작한 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            plantId = PLANT_ID,
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
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("키우기 시작한 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            plantId = PLANT_ID,
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
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("마지막으로 물 준 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 비료 준 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantSaveRequest(
                            plantId = PLANT_ID,
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
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/plants") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("마지막으로 비료 준 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
        }

        describe("내 식물 수정") {
            context("키우기 시작한 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = FUTURE_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/plants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("키우기 시작한 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 물 준 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = FUTURE_DATE,
                            lastFertilizerDate = LAST_FERTILIZER_DATE,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/plants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("마지막으로 물 준 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
            context("마지막으로 비료 준 날짜를 미래로 전달하면") {
                val json =
                    objectMapper.writeValueAsString(
                        MyPlantModifyRequest(
                            nickname = NICKNAME,
                            locationId = LOCATION_ID,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            lastFertilizerDate = FUTURE_DATE,
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.patch("/api/v1/plants/$MYPLANT_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = json
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("마지막으로 비료 준 날짜는 미래일 수 없습니다."))
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val ERROR_CODE = ErrorType.ARGUMENT_ERROR.name
        const val PLANT_ID = 1L
        const val MYPLANT_ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        const val LOCATION_ID = 100L
        val FUTURE_DATE: LocalDate = LocalDate.now().plusDays(1)
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)
        val LAST_FERTILIZER_DATE: LocalDate = LocalDate.of(2024, 6, 15)
        const val WATER_ALARM = true
        const val WATER_PERIOD = 3
        const val FERTILIZER_ALARM = false
        const val FERTILIZER_PERIOD = 30
        const val HEALTHCHECK_ALARM = true
    }
}
