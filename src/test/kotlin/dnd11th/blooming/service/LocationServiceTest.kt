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

                    response.name shouldBe LOCATION_NAME
                }
            }
        }
        describe("위치 전체 조회") {
            every { locationRepository.findAll() } returns
                listOf(
                    Location(
                        name = LOCATION_NAME,
                    ),
                    Location(
                        name = LOCATION_NAME2,
                    ),
                )
            context("위치를 전체 조회하면") {
                it("모든 위치가 조회되어야 한다.") {
                    val response = locationService.findAllLocation()

                    response.size shouldBe 2
                    response[0].name shouldBe LOCATION_NAME
                    response[1].name shouldBe LOCATION_NAME2
                }
            }
        }
    },
) {
    companion object {
        const val ID = 1L
        const val LOCATION_NAME = "거실"
        const val LOCATION_NAME2 = "베란다"
    }
}
