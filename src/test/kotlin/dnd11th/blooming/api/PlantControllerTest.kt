package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.controller.PlantController
import dnd11th.blooming.api.dto.PlantResponse
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.api.service.PlantService
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
            every { plantService.savePlant(any()) } returns
                PlantSaveResponse(
                    id = ID,
                )
            expect("식물이 정상적으로 저장되어야 한다.") {
                val json =
                    objectMapper.writeValueAsString(
                        PlantSaveRequest(
                            scientificName = SCIENTIFIC_NAME,
                            name = NAME,
                            startDate = START_DATE,
                            lastWateredDate = LAST_WATERED_DATE,
                            waterAlarm = true,
                            nutrientsAlarm = false,
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
    }
}
