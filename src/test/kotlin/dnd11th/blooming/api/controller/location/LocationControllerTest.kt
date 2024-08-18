package dnd11th.blooming.api.controller.location

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationResponse
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.dto.location.LocationSaveResponse
import dnd11th.blooming.api.service.location.LocationService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.jwt.JwtProvider
import dnd11th.blooming.domain.repository.user.UserRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@WebMvcTest(LocationController::class)
@ActiveProfiles("test")
class LocationControllerTest : DescribeSpec() {
    @MockkBean
    private lateinit var locationService: LocationService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @MockkBean
    private lateinit var userRepository: UserRepository

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
            context("정상 요청을 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationSaveRequest(
                            name = LOCATION_NAME,
                        ),
                    )
                it("정상 응답이 반환되어야 한다.") {
                    mockMvc.post("/api/v1/location") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                        jsonPath("$.id", equalTo(LOCATION_ID.toInt()))
                        jsonPath("$.name", equalTo(LOCATION_NAME))
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
                    mockMvc.get("/api/v1/location")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.size()", equalTo(2))
                            jsonPath("$[0].id", equalTo(LOCATION_ID.toInt()))
                            jsonPath("$[0].name", equalTo(LOCATION_NAME))
                            jsonPath("$[1].id", equalTo(LOCATION_ID2.toInt()))
                            jsonPath("$[1].name", equalTo(LOCATION_NAME2))
                        }
                }
            }
        }

        describe("위치 수정") {
            beforeTest {
                every { locationService.modifyLocation(LOCATION_ID, any()) } returns
                    LocationResponse(
                        id = LOCATION_ID,
                        name = LOCATION_NAME2,
                    )
                every { locationService.modifyLocation(not(eq(LOCATION_ID)), any()) } throws
                    NotFoundException(ErrorType.NOT_FOUND_LOCATION)
            }
            context("존재하는 위치로 위치 수정 요청을 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationModifyRequest(
                            name = LOCATION_NAME2,
                        ),
                    )
                it("수정된 위치가 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/location/$LOCATION_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isOk() }
                        jsonPath("$.id", equalTo(LOCATION_ID.toInt()))
                        jsonPath("$.name", equalTo(LOCATION_NAME2))
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
                    mockMvc.patch("/api/v1/location/$LOCATION_ID2") {
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

        describe("위치 삭제") {
            beforeTest {
                every { locationService.deleteLocation(LOCATION_ID) } just runs
                every { locationService.deleteLocation(not(eq(LOCATION_ID))) } throws
                    NotFoundException(ErrorType.NOT_FOUND_LOCATION)
            }
            context("존재하는 위치로 위치 삭제 요청을 전달하면") {
                it("정상 응답이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/location/$LOCATION_ID")
                        .andExpectAll {
                            status { isOk() }
                        }.andDo { print() }
                }
            }
            context("존재하지 않는 위치로 위치 삭제 요청을 전달하면") {
                it("예외 응답이 반환되어야 한다.") {
                    mockMvc.delete("/api/v1/location/$LOCATION_ID2")
                        .andExpectAll {
                            status { isNotFound() }
                            jsonPath("$.message", equalTo("존재하지 않는 위치입니다."))
                            jsonPath("$.code", equalTo(ErrorType.NOT_FOUND_LOCATION.name))
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
