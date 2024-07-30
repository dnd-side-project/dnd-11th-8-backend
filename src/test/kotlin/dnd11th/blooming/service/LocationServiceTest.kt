package dnd11th.blooming.service

import dnd11th.blooming.api.dto.LocationModifyRequest
import dnd11th.blooming.api.dto.LocationSaveRequest
import dnd11th.blooming.api.service.LocationService
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.repository.LocationRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull

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
        describe("위치 수정") {
            beforeTest {
                every { locationRepository.findByIdOrNull(LOCATION_ID) } returns
                    Location(
                        name = LOCATION_NAME,
                    ).apply {
                        id = LOCATION_ID
                    }
                every { locationRepository.findByIdOrNull(not(eq(LOCATION_ID))) } returns
                    null
            }
            context("존재하는 ID의 위치를 수정하면") {
                val request =
                    LocationModifyRequest(
                        name = MODIFIED_LOCATION_NAME,
                    )
                it("위치가 수정되고, 수정된 위치가 조회되어야 한다.") {
                    val response = locationService.modifyLocation(LOCATION_ID, request)
                    response.id shouldBe LOCATION_ID
                    response.name shouldBe MODIFIED_LOCATION_NAME
                }
            }
            context("존재하지 않는 ID의 위치를 수정하면") {
                val request =
                    LocationModifyRequest(
                        name = MODIFIED_LOCATION_NAME,
                    )
                it("NotFoundException(NOT_FOUND_LOCATION_ID) 예외가 발생해야 한다.") {
                    val exception =
                        shouldThrow<NotFoundException> {
                            locationService.modifyLocation(LOCATION_ID2, request)
                        }
                    exception.message shouldBe "존재하지 않는 위치입니다."
                    exception.errorType shouldBe ErrorType.NOT_FOUND_LOCATION_ID
                }
            }
        }
    },
) {
    companion object {
        const val LOCATION_ID = 1L
        const val LOCATION_ID2 = 2L
        const val LOCATION_NAME = "거실"
        const val LOCATION_NAME2 = "베란다"
        const val MODIFIED_LOCATION_NAME = "안방"
    }
}
