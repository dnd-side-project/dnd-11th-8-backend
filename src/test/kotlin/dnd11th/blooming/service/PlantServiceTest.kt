package dnd11th.blooming.service

import dnd11th.blooming.api.dto.PlantSaveRequest
import dnd11th.blooming.api.dto.PlantSaveResponse
import dnd11th.blooming.api.service.PlantService
import dnd11th.blooming.service.implement.PlantWriter
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class PlantServiceTest : BehaviorSpec({
    val plantWriter = mockk<PlantWriter>()
    val plantService = PlantService(plantWriter)

    Context("식물 저장") {
        beforeTest {
            every { plantWriter.saveOne(any()) } returns
                PlantSaveResponse(
                    id = ID,
                )
        }
        Given("정상 요청이 왔을 때") {
            val request =
                PlantSaveRequest(
                    scientificName = SCIENTIFIC_NAME,
                    name = NAME,
                    startDate = START_DATE,
                    lastWateredDate = LAST_WATERED_DATE,
                    waterAlarm = true,
                    nutrientsAlarm = false,
                )
            When("식물을 저장하면") {
                val response = plantService.savePlant(request)
                Then("정상적으로 저장되어야 한다.") {
                    response.id shouldBe 1
                }
            }
        }
    }
}) {
    companion object {
        const val ID = 1L
        const val SCIENTIFIC_NAME = "몬스테라 델리오사"
        const val NAME = "뿡뿡이"
        val START_DATE: LocalDate = LocalDate.of(2024, 4, 19)
        val LAST_WATERED_DATE: LocalDate = LocalDate.of(2024, 6, 29)
    }
}
