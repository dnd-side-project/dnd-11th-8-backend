package dnd11th.blooming.service

import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.service.LocationService
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.repository.LocationRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class LocationServiceTest : DescribeSpec(
    {
        val locationRepository = mockk<LocationRepository>()
        val locationService = LocationService(locationRepository)

        describe("위치 저장") {
            every { locationRepository.save(any()) } returns
                Location(
                    name = LOCATION_NAME,
                )
            context("name이 전달되면") {
                val request =
                    LocationSaveRequest(
                        name = "거실",
                    )
                it("위치가 저장되어야 한다.") {
                    val response = locationService.saveLocation(request)

                    response.id shouldBe 0L
                    response.name shouldBe "거실"
                }
            }
        }
    },
) {
    companion object {
        const val ID = 1L
        const val LOCATION_NAME = "거실"
    }
}
