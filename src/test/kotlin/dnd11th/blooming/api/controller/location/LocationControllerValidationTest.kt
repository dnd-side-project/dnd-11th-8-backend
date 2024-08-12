package dnd11th.blooming.api.controller.location

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.api.service.location.LocationService
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

@WebMvcTest(LocationController::class)
class LocationControllerValidationTest : DescribeSpec() {
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
            context("위치명을 비우고 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationSaveRequest(
                            name = "",
                        ),
                    )
                it("예외 응답이 반환되어야 한다.") {
                    mockMvc.post("/api/v1/location") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("새로운 위치명은 비어있을 수 없습니다."))
                    }.andDo { print() }
                }
            }
        }

        describe("위치 이름 수정") {
            context("위치명을 비우고 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationModifyRequest(
                            name = "",
                        ),
                    )
                it("수정된 위치가 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/location/$LOCATION_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("새로운 위치명은 비어있을 수 없습니다."))
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val ERROR_CODE = ErrorType.ARGUMENT_ERROR.name

        const val LOCATION_ID = 1L
    }
}
