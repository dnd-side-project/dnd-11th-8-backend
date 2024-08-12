package dnd11th.blooming.api.controller.image

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.image.ImageSaveRequest
import dnd11th.blooming.api.service.image.ImageService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(ImageController::class)
class ImageControllerValidationTest : DescribeSpec() {
    @MockkBean
    private lateinit var imageService: ImageService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        describe("이미지 저장") {
            context("이미지URL을 비우고 전달하면") {
                val request =
                    objectMapper.writeValueAsString(
                        ImageSaveRequest(
                            imageUrl = "",
                        ),
                    )
                it("예외 응답이 와야 한다.") {
                    mockMvc.post("/api/v1/myplants/1/image") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }.andExpectAll {
                        status { isBadRequest() }
                        jsonPath("$.code", equalTo(ERROR_CODE))
                        jsonPath("$.message", equalTo("URL은 비어있을 수 없습니다."))
                    }.andDo { print() }
                }
            }
        }
    }

    companion object {
        val ERROR_CODE = ErrorType.ARGUMENT_ERROR.name
    }
}
