package dnd11th.blooming.api.controller.home

import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.api.dto.home.MyPlantHomeResponse
import dnd11th.blooming.api.support.WebMvcDescribeSpec
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.test.web.servlet.get

class HomeControllerTest : WebMvcDescribeSpec() {
    init {
        describe("홈 화면") {
            every { homeService.getHome(any(), any()) } returns
                HomeResponse(
                    "안녕하세요.",
                    listOf(
                        MyPlantHomeResponse(
                            myPlantId = 1,
                            nickname = "뿡뿡이",
                            scientificName = "몬스테라 델리오사",
                            dateSinceLastWater = 10,
                            dateSinceLastFertilizer = 10,
                            dateSinceLasthealthCheck = 10,
                        ),
                        MyPlantHomeResponse(
                            myPlantId = 2,
                            nickname = "빵빵이",
                            scientificName = "병아리눈물",
                            dateSinceLastWater = 10,
                            dateSinceLastFertilizer = 10,
                            dateSinceLasthealthCheck = 10,
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
