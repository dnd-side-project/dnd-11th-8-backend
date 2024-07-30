package dnd11th.blooming.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.controller.LocationController
import dnd11th.blooming.api.dto.LocationModifyRequest
import dnd11th.blooming.api.dto.LocationResponse
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.dto.LocationSaveResponse
import dnd11th.blooming.api.service.LocationService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
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
        describe("위치 전체 조회") {
            every { locationService.findAllLocation() } returns
                listOf(
                    LocationResponse(
                        id = LOCATION_ID,
                        name = LOCATION_NAME,
                    ),
                    LocationResponse(
                        id = LOCATION_ID2,
                        name = LOCATION_NAME2,
                    ),
                )
            context("위치를 전체 조회하면") {
                it("위치 리스트가 조회되어야 한다.") {
                    mockMvc.get("/location")
                        .andExpectAll {
                            status { isOk() }
                            MockMvcResultMatchers.jsonPath("$.size()").value(2)
                            MockMvcResultMatchers.jsonPath("$[0].id").value(LOCATION_ID)
                            MockMvcResultMatchers.jsonPath("$[0].name").value(LOCATION_NAME)
                            MockMvcResultMatchers.jsonPath("$[1].id").value(LOCATION_ID2)
                            MockMvcResultMatchers.jsonPath("$[1].name").value(LOCATION_NAME2)
                        }
                }
            }
        }
        describe("위치 수정") {
            beforeTest {
                every { locationService.modifyLocation(LOCATION_ID, any()) } returns
                    LocationResponse(
                        id = LOCATION_ID,
                        name = LOCATION_NAME,
                    )
                every { locationService.modifyLocation(not(eq(LOCATION_ID)), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_LOCATION_ID)
            }
            context("존재하는 위치로 위치 수정 요청을 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationModifyRequest(
                            name = LOCATION_NAME2,
                        ),
                    )
                it("수정된 위치가 반환되어야 한다.") {
                    mockMvc.patch("/location/$LOCATION_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                        MockMvcResultMatchers.jsonPath("$.id").value(LOCATION_ID)
                        MockMvcResultMatchers.jsonPath("$.name").value(LOCATION_NAME2)
                    }.andDo { print() }
                }
            }
            context("존재하지 않는 위치로 위치 수정 요청을 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationModifyRequest(
                            name = LOCATION_NAME2,
                        ),
                    )
                it("예외 응답이 반환되어야 한다.") {
                    mockMvc.patch("/location/$LOCATION_ID2") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isNotFound() }
                        MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 위치입니다.")
                        MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.NOT_FOUND_LOCATION_ID)
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        const val LOCATION_ID = 1L
        const val LOCATION_NAME = "거실"

        const val LOCATION_ID2 = 2L
        const val LOCATION_NAME2 = "베란다"
    }
}
