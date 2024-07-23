package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.service.PlantService
import io.kotest.core.spec.style.ExpectSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
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
    }

    companion object {
        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)
    }
}
