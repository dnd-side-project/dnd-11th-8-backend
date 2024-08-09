package dnd11th.blooming.api.controller.home

import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.api.dto.home.MyPlantHomeResponse
import dnd11th.blooming.api.service.home.HomeService
import dnd11th.blooming.common.jwt.JwtProvider
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(HomeController::class)
class HomeControllerTest : DescribeSpec() {
    @MockkBean
    private lateinit var homeService: HomeService

    @MockkBean
    private lateinit var jwtProvider: JwtProvider

    @Autowired
    private lateinit var mockMvc: MockMvc

    init {
        describe("홈 화면") {
            every { homeService.getHome(any()) } returns
                HomeResponse(
                    "안녕하세요.",
                    listOf(
                        MyPlantHomeResponse(
                            myPlantId = 1,
                            nickname = "뿡뿡이",
                            scientificName = "몬스테라 델리오사",
                            waterAlarm = true,
                            waterRemainDay = 2,
                            fertilizerAlarm = true,
                            fertilizerRemainDay = 30,
                            healthCheckAlarm = true,
                        ),
                        MyPlantHomeResponse(
                            myPlantId = 2,
                            nickname = "빵빵이",
                            scientificName = "병아리눈물",
                            waterAlarm = false,
                            waterRemainDay = 10,
                            fertilizerAlarm = false,
                            fertilizerRemainDay = 10,
                            healthCheckAlarm = false,
                        ),
                    ),
                )
            context("홈 화면을 요청하면") {
                it("인삿말과 내 식물 정보를 볼 수 있다.") {
                    mockMvc.get("/api/v1/home")
                        .andExpectAll {
                            status { isOk() }
                            jsonPath("$.greetingMessage", equalTo("안녕하세요."))
                            jsonPath("$.myPlantInfo.size()", equalTo(2))
                            jsonPath("$.myPlantInfo[0].myPlantId", equalTo(1))
                            jsonPath("$.myPlantInfo[1].myPlantId", equalTo(2))
                        }
                }
            }
        }
    }
}
