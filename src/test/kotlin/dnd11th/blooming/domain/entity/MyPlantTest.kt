package dnd11th.blooming.domain.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class MyPlantTest : DescribeSpec(
    {
        describe("다음 물주기까지 남은 기간 확인") {
            val lastwaterDate = LocalDate.of(2024, 5, 17)
            val lastfertilizerDate = LocalDate.of(2024, 5, 5)
            val now = LocalDate.of(2024, 5, 20)

            context("waterAlarm이 true일때") {
                val myplant =
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = lastwaterDate,
                        lastFertilizerDate = lastfertilizerDate,
                        alarm =
                            Alarm(
                                waterAlarm = true,
                                waterPeriod = 4,
                                fertilizerAlarm = true,
                                fertilizerPeriod = 30,
                                healthCheckAlarm = true,
                            ),
                    )
                it("다음 물주기까지 남은 날짜를 확인할 수 있어야 한다.") {
                    val result = myplant.getWaterRemainDay(now)
                    result shouldBe 1
                }
            }
            context("waterAlarm이 false일때") {
                val myplant =
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = lastwaterDate,
                        lastFertilizerDate = LAST_WATERED_DATE,
                        alarm =
                            Alarm(
                                waterAlarm = false,
                                waterPeriod = 3,
                                fertilizerAlarm = true,
                                fertilizerPeriod = 30,
                                healthCheckAlarm = true,
                            ),
                    )
                it("다음 물주기까지 남은 날짜는 null이다.") {
                    val result = myplant.getWaterRemainDay(now)
                    result shouldBe null
                }
            }
        }

        describe("내 식물에서 다음 비료까지 남은 기간을 알 수 있다.") {
            val lastwaterDate = LocalDate.of(2024, 5, 17)
            val lastfertilizerDate = LocalDate.of(2024, 5, 5)
            val now = LocalDate.of(2024, 5, 20)

            context("fertilizerAlarm이 true일때") {
                val myplant =
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = lastwaterDate,
                        lastFertilizerDate = lastfertilizerDate,
                        alarm =
                            Alarm(
                                waterAlarm = true,
                                waterPeriod = 3,
                                fertilizerAlarm = true,
                                fertilizerPeriod = 30,
                                healthCheckAlarm = true,
                            ),
                    )
                it("다음 비료까지 남은 날짜를 확인할 수 있어야 한다.") {
                    val result = myplant.getFerilizerRemainDate(now)
                    result shouldBe 15
                }
            }
            context("fertilizerAlarm이 false일때") {
                val myplant =
                    MyPlant(
                        scientificName = SCIENTIFIC_NAME,
                        nickname = NICKNAME,
                        startDate = START_DATE,
                        lastWateredDate = lastwaterDate,
                        lastFertilizerDate = lastfertilizerDate,
                        alarm =
                            Alarm(
                                waterAlarm = true,
                                waterPeriod = 3,
                                fertilizerAlarm = false,
                                fertilizerPeriod = 30,
                                healthCheckAlarm = true,
                            ),
                    )
                it("다음 비료까지 남은 날짜는 null이어야 한다.") {
                    val result = myplant.getFerilizerRemainDate(now)
                    result shouldBe null
                }
            }
        }
    },
) {
    companion object {
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NICKNAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)
    }
}
