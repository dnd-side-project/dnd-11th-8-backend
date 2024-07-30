package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.controller.LocationController
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.api.service.LocationService
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(LocationController::class)
class LocationControllerTest : DescribeSpec() {
    @MockkBean
    private lateinit var locationService: LocationService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        describe("위치 저장") {
            every { locationService.saveLocation(any()) } returns
                LocationSaveResponse(
                    id = LOCATION_ID,
                    name = LOCATION_NAME,
                )
            context("name을 request로 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationSaveRequest(
                            name = LOCATION_NAME,
                        ),
                    )
                it("정상 응답이 반환되어야 한다.") {
                    mockMvc.post("/location") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.id").value(LOCATION_ID)
                        MockMvcResultMatchers.jsonPath("$.name").value(LOCATION_NAME)
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        const val LOCATION_ID = 1L
        const val LOCATION_NAME = "거실"
    }
}
