package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.controller.MyPlantController
import dnd11th.blooming.api.dto.MyPlantDetailResponse
import dnd11th.blooming.api.dto.MyPlantResponse
import dnd11th.blooming.api.dto.MyPlantSaveRequest
import dnd11th.blooming.api.dto.MyPlantSaveResponse
import dnd11th.blooming.api.service.MyPlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.ExpectSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@WebMvcTest(MyPlantController::class)
class PlantControllerTest : ExpectSpec() {
    @MockkBean
    private lateinit var myPlantService: MyPlantService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        context("내 식물 저장") {
            beforeTest {
                every { myPlantService.savePlant(any()) } returns
                    MyPlantSaveResponse(
                        id = ID,
                    )
                every {
                    myPlantService.savePlant(
                        match {
                            it.startDate == FUTURE_DATE || it.lastWateredDate == FUTURE_DATE
                        },
                    )
                } throws
                    InvalidDateException(ErrorType.INVALID_DATE)
            }
            expect("내 식물이 정상적으로 저장되어야 한다.") {
                val json =
                    objectMapper.writeValueAsString(
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
                        ),
                    )
                mockMvc.post("/api/v1/plants") {
                    contentType = MediaType.APPLICATION_JSON
                    content = json
                }.andExpectAll {
                    status { isOk() }
                    MockMvcResultMatchers.jsonPath("$.id").value(ID)
                }.andDo { print() }
            }
            expect("시작날짜가 미래라면 예외응답이 반환되어야 한다.") {
                val json =
                    objectMapper.writeValueAsString(
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
                        ),
                    )
                mockMvc.post("/api/v1/plants") {
                    contentType = MediaType.APPLICATION_JSON
                    content = json
                }.andExpectAll {
                    status { isBadRequest() }
                    MockMvcResultMatchers.jsonPath("$.message").value("올바르지 않은 날짜입니다.")
                    MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.INVALID_DATE)
                }.andDo { print() }
            }
            expect("마지막으로 물 준 날짜가 미래라면 예외응답이 반환되어야 한다.") {
                val json =
                    objectMapper.writeValueAsString(
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
                        ),
                    )
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

        context("내 식물 전체 조회") {
            every { myPlantService.findAllPlant() } returns
                listOf(
                    MyPlantResponse(
                        id = ID,
                        nickname = NICKNAME,
                        scientificName = SCIENTIFIC_NAME,
                    ),
                    MyPlantResponse(
                        id = ID2,
                        nickname = NICKNAME2,
                        scientificName = SCIENTIFIC_NAME2,
                    ),
                )
            expect("내 모든 식물이 조회되어야 한다.") {
                mockMvc.get("/api/v1/plants")
                    .andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.size()").value(2)
                        MockMvcResultMatchers.jsonPath("$[0].id").value(ID)
                        MockMvcResultMatchers.jsonPath("$[0].name").value(NICKNAME)
                        MockMvcResultMatchers.jsonPath("$[0].scientificName").value(SCIENTIFIC_NAME)
                        MockMvcResultMatchers.jsonPath("$[1].id").value(ID2)
                        MockMvcResultMatchers.jsonPath("$[1].name").value(NICKNAME2)
                        MockMvcResultMatchers.jsonPath("$[1].scientificName").value(SCIENTIFIC_NAME2)
                    }.andDo { print() }
            }
        }

        context("내 식물 상세 조회") {
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

            expect("존재하는 id로 조회하면 내 식물이 조회되어야 한다.") {
                mockMvc.get("/api/v1/plants/$ID")
                    .andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.name").value(NICKNAME)
                        MockMvcResultMatchers.jsonPath("$.scientificName").value(SCIENTIFIC_NAME)
                        MockMvcResultMatchers.jsonPath("$.startDate").value(START_DATE)
                        MockMvcResultMatchers.jsonPath("$.lastWatedDate").value(LAST_WATERED_DATE)
                    }.andDo { print() }
            }
            expect("존재하지 않는 id로 조회하면 예외응답이 반한되어야 한다.") {
                mockMvc.get("/api/v1/plants/$ID2")
                    .andExpectAll {
                        status { isNotFound() }
                        MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 내 식물입니다.")
                        MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_MYPLANT_ID)
                    }.andDo { print() }
            }
        }
    }

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
