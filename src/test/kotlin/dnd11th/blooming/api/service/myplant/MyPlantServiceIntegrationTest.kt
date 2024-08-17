package dnd11th.blooming.api.service.myplant

import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.domain.entity.Alarm
import dnd11th.blooming.domain.entity.Image
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.repository.ImageRepository
import dnd11th.blooming.domain.repository.LocationRepository
import dnd11th.blooming.domain.repository.MyPlantRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
class MyPlantServiceIntegrationTest : DescribeSpec() {
    @Autowired
    lateinit var myPlantService: MyPlantService

    @Autowired
    lateinit var myPlantRepository: MyPlantRepository

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Autowired
    lateinit var locationRepository: LocationRepository

    init {
        afterTest {
            imageRepository.deleteAllInBatch()
            myPlantRepository.deleteAllInBatch()
            locationRepository.deleteAllInBatch()
        }

        describe("최근생성순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                    ),
                )

            val pastPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME,
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME,
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val pastImage =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                    ).also {
                        it.myPlant = pastPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url5",
                    favorite = false,
                ).also {
                    it.myPlant = pastPlant
                },
            )

            val recentImage =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                    ).also {
                        it.myPlant = recentPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url2",
                    favorite = true,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            imageRepository.save(
                Image(
                    url = "url3",
                    favorite = false,
                ).also {
                    it.myPlant = recentPlant
                },
            )
            context("최근생성순 식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = DATE_TIME,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.CreatedDesc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe recentPlant.nickname
                    result[0].imageUrl shouldBe recentImage.url
                    result[1].nickname shouldBe pastPlant.nickname
                    result[1].imageUrl shouldBe pastImage.url
                }
            }
        }
        describe("오래된 생성순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                    ),
                )

            val pastPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME,
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME,
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val pastImage =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                    ).also {
                        it.myPlant = pastPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url5",
                    favorite = false,
                ).also {
                    it.myPlant = pastPlant
                },
            )

            val recentImage =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                    ).also {
                        it.myPlant = recentPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url2",
                    favorite = true,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            imageRepository.save(
                Image(
                    url = "url3",
                    favorite = false,
                ).also {
                    it.myPlant = recentPlant
                },
            )
            context("오래된 생성순 식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = DATE_TIME,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.CreatedAsc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe pastPlant.nickname
                    result[0].imageUrl shouldBe pastImage.url
                    result[1].nickname shouldBe recentPlant.nickname
                    result[1].imageUrl shouldBe recentImage.url
                }
            }
        }
        describe("최근 물주기순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME.minusDays(5),
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val pastPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME.minusDays(10),
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val recentImage =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                    ).also {
                        it.myPlant = recentPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url2",
                    favorite = true,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            imageRepository.save(
                Image(
                    url = "url3",
                    favorite = false,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            val pastImage =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                    ).also {
                        it.myPlant = pastPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url5",
                    favorite = false,
                ).also {
                    it.myPlant = pastPlant
                },
            )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = DATE_TIME,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.WateredDesc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe recentPlant.nickname
                    result[0].imageUrl shouldBe recentImage.url
                    result[1].nickname shouldBe pastPlant.nickname
                    result[1].imageUrl shouldBe pastImage.url
                }
            }
        }
        describe("오래된 물주기순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME.minusDays(5),
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val pastPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = DATE_TIME,
                        lastWateredDate = DATE_TIME.minusDays(10),
                        lastFertilizerDate = DATE_TIME,
                        lastHealthCheckDate = DATE_TIME,
                        alarm = Alarm(),
                    ).also {
                        it.location = location
                    },
                )

            val recentImage =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                    ).also {
                        it.myPlant = recentPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url2",
                    favorite = true,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            imageRepository.save(
                Image(
                    url = "url3",
                    favorite = false,
                ).also {
                    it.myPlant = recentPlant
                },
            )

            val pastImage =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                    ).also {
                        it.myPlant = pastPlant
                    },
                )

            imageRepository.save(
                Image(
                    url = "url5",
                    favorite = false,
                ).also {
                    it.myPlant = pastPlant
                },
            )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = DATE_TIME,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.WateredAsc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe pastPlant.nickname
                    result[0].imageUrl shouldBe pastImage.url
                    result[1].nickname shouldBe recentPlant.nickname
                    result[1].imageUrl shouldBe recentImage.url
                }
            }
        }
    }

    companion object {
        val CURRENT_TIME: LocalDateTime = LocalDateTime.of(2000, 5, 17, 12, 0, 0)
        val DATE_TIME: LocalDate = CURRENT_TIME.toLocalDate().minusDays(10)
    }
}
