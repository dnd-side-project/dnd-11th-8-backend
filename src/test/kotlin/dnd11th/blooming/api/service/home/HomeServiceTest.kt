package dnd11th.blooming.api.service.home

import dnd11th.blooming.api.dto.home.MyPlantHomeResponse
import dnd11th.blooming.api.service.myplant.MyPlantMessageFactory
import dnd11th.blooming.api.service.myplant.MyPlantServiceTest.Companion.ALARM
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.MyPlantRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class HomeServiceTest : DescribeSpec(
    {
        val myPlantRepository = mockk<MyPlantRepository>()
        val messageFactory = mockk<MyPlantMessageFactory>()
        val homeService = HomeService(myPlantRepository, messageFactory)

        describe("홈 화면") {
            val user =
                User(
                    "email",
                    "nickname",
                    AlarmTime.TIME_12_13,
                    100,
                    100,
                )
            val plant1 =
                MyPlant(
                    scientificName = "병아리눈물",
                    nickname = "일번",
                    lastWateredDate = LocalDate.of(2024, 5, 16),
                    lastFertilizerDate = LocalDate.of(2024, 5, 16),
                    lastHealthCheckDate = LocalDate.of(2024, 5, 16),
                    startDate = LocalDate.of(2024, 5, 16),
                    alarm = ALARM,
                ).apply {
                    id = 1
                    location = LOCATION1
                }
            val plant2 =
                MyPlant(
                    scientificName = "몬스테라 델리오사",
                    nickname = "이번",
                    lastWateredDate = LocalDate.of(2024, 5, 17),
                    lastFertilizerDate = LocalDate.of(2024, 5, 16),
                    lastHealthCheckDate = LocalDate.of(2024, 5, 16),
                    startDate = LocalDate.of(2024, 5, 16),
                    alarm = ALARM,
                ).apply {
                    id = 2
                    location = LOCATION1
                }
            val plant3 =
                MyPlant(
                    scientificName = "선인장",
                    nickname = "삼번",
                    lastWateredDate = LocalDate.of(2024, 5, 15),
                    lastFertilizerDate = LocalDate.of(2024, 5, 16),
                    lastHealthCheckDate = LocalDate.of(2024, 5, 16),
                    startDate = LocalDate.of(2024, 5, 16),
                    alarm = ALARM,
                ).apply {
                    id = 3
                    location = LOCATION2
                }
            every { messageFactory.createGreetingMessage(any()) } returns
                "안녕하세요."
            every { myPlantRepository.findAllByUser(any()) } returns
                listOf(plant1, plant2, plant3)
            context("홈 화면") {
                it("인삿말과 내 식물 정보를 볼 수 있다.") {
                    val result = homeService.getHome(user, CURRENT_DAY)

                    result.greetingMessage shouldBe "안녕하세요."
                    result.myPlantInfo shouldContainExactlyInAnyOrder
                        listOf(
                            MyPlantHomeResponse.of(plant1, CURRENT_DAY),
                            MyPlantHomeResponse.of(plant2, CURRENT_DAY),
                            MyPlantHomeResponse.of(plant3, CURRENT_DAY),
                        )
                }
            }
        }
    },
) {
    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.of(2024, 5, 17)
        val LOCATION1 = Location(name = "창문")
        val LOCATION2 = Location(name = "거실")
    }
}
