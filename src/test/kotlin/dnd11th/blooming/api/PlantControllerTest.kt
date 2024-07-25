package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.controller.PlantController
import dnd11th.blooming.api.dto.PlantDetailResponse
import dnd11th.blooming.api.dto.PlantResponse
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.api.service.PlantService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.InvalidDateException
import dnd11th.blooming.common.exception.NotFoundException
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

@WebMvcTest(PlantController::class)
class PlantControllerTest : ExpectSpec() {
    @MockkBean
    private lateinit var plantService: PlantService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        context("식물 저장") {
            beforeTest {
                every { plantService.savePlant(any()) } returns
                    PlantSaveResponse(
                        id = ID,
                    )
                every {
                    plantService.savePlant(
                        match {
                            it.startDate == FUTURE_DATE || it.lastWateredDate == FUTURE_DATE
                        },
                    )
                } throws
                    InvalidDateException(ErrorType.INVALID_DATE)
            }
            expect("식물이 정상적으로 저장되어야 한다.") {
                val json =
                    objectMapper.writeValueAsString(
                        PlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            name = NAME,
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
                mockMvc.post("/plant") {
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
                        PlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            name = NAME,
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
                mockMvc.post("/plant") {
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
                        PlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            name = NAME,
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
                mockMvc.post("/plant") {
                    contentType = MediaType.APPLICATION_JSON
                    content = json
                }.andExpectAll {
                    status { isBadRequest() }
                    MockMvcResultMatchers.jsonPath("$.message").value("올바르지 않은 날짜입니다.")
                    MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.INVALID_DATE)
                }.andDo { print() }
            }
        }

        context("식물 전체 조회") {
            every { plantService.findAllPlant() } returns
                listOf(
                    PlantResponse(
                        id = ID,
                        name = NAME,
                        scientificName = SCIENTIFIC_NAME,
                    ),
                    PlantResponse(
                        id = ID2,
                        name = NAME2,
                        scientificName = SCIENTIFIC_NAME2,
                    ),
                )
            expect("모든 식물이 조회되어야 한다.") {
                mockMvc.get("/plants")
                    .andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.size()").value(2)
                        MockMvcResultMatchers.jsonPath("$[0].id").value(ID)
                        MockMvcResultMatchers.jsonPath("$[0].name").value(NAME)
                        MockMvcResultMatchers.jsonPath("$[0].scientificName").value(SCIENTIFIC_NAME)
                        MockMvcResultMatchers.jsonPath("$[1].id").value(ID2)
                        MockMvcResultMatchers.jsonPath("$[1].name").value(NAME2)
                        MockMvcResultMatchers.jsonPath("$[1].scientificName").value(SCIENTIFIC_NAME2)
                    }.andDo { print() }
            }
        }

        context("식물 상세 조회") {
            beforeTest {
                every { plantService.findPlantDetail(ID) } returns
                    PlantDetailResponse(
                        name = NAME,
                        scientificName = SCIENTIFIC_NAME,
                        startDate = START_DATE,
                        lastWatedDate = LAST_WATERED_DATE,
                    )
                every { plantService.findPlantDetail(ID2) } throws
                    NotFoundException(ErrorType.NOT_FOUND_PLANT_ID)
            }

            expect("존재하는 id로 조회하면 식물이 조회되어야 한다.") {
                mockMvc.get("/plant/$ID")
                    .andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.name").value(NAME)
                        MockMvcResultMatchers.jsonPath("$.scientificName").value(SCIENTIFIC_NAME)
                        MockMvcResultMatchers.jsonPath("$.startDate").value(START_DATE)
                        MockMvcResultMatchers.jsonPath("$.lastWatedDate").value(LAST_WATERED_DATE)
                    }.andDo { print() }
            }
            expect("존재하지 않는 id로 조회하면 예외응답이 반한되어야 한다.") {
                mockMvc.get("/plant/$ID2")
                    .andExpectAll {
                        status { isNotFound() }
                        MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 식물입니다.")
                        MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_PLANT_ID)
                    }.andDo { print() }
            }
        }
    }

    companion object {
        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)

        const val ID2 = 2L
        const val SCIENTIFIC_NAME2 = "병아리 눈물"
        const val NAME2 = "빵빵이"
        val START_DATE2: LocalDate = LocalDate.of(2024, 3, 20)
        val LAST_WATERED_DATE2: LocalDate = LocalDate.of(2024, 7, 20)

        val FUTURE_DATE: LocalDate = LocalDate.of(5000, 5, 17)
    }
}
