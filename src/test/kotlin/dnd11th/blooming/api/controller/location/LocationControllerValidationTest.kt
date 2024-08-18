package dnd11th.blooming.api.controller.location

import dnd11th.blooming.api.dto.location.LocationModifyRequest
import dnd11th.blooming.api.dto.location.LocationSaveRequest
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.support.WebMvcDescribeSpec
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

class LocationControllerValidationTest : WebMvcDescribeSpec() {
    init {
        describe("위치 저장") {
            context("위치명을 전달하지 않으면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationSaveRequest(
                            name = null,
                        ),
                    )
                it("예외 응답이 반환되어야 한다.") {
                    mockMvc.post("/api/v1/location") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("새로운 위치명은 필수값입니다."))
                    }.andDo { print() }
                }
            }
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
                        jsonPath("$.message", equalTo("새로운 위치명은 필수값입니다."))
                    }.andDo { print() }
                }
            }
        }

        describe("위치 이름 수정") {
            context("위치명을 전달하지 않으면") {
                val request =
                    objectMapper.writeValueAsString(
                        LocationModifyRequest(
                            name = null,
                        ),
                    )
                it("수정된 위치가 반환되어야 한다.") {
                    mockMvc.patch("/api/v1/location/$LOCATION_ID") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("새로운 위치명은 필수값입니다."))
                    }.andDo { print() }
                }
            }
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
                        jsonPath("$.message", equalTo("새로운 위치명은 필수값입니다."))
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val ERROR_CODE = ErrorType.BAD_REQUEST.name

        const val LOCATION_ID = 1L
    }
}
