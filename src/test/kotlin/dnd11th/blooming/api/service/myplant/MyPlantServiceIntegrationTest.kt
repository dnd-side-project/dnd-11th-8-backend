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
import java.time.LocalDate

@SpringBootTest
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
                        currentDate = CURRENT_DAY,
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY,
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY.minusDays(5),
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val latePlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY,
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY.minusDays(10),
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val image1 =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image2 =
                imageRepository.save(
                    Image(
                        url = "url2",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image3 =
                imageRepository.save(
                    Image(
                        url = "url3",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image4 =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )

            val image5 =
                imageRepository.save(
                    Image(
                        url = "url5",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = CURRENT_DAY,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.CreatedDesc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe recentPlant.nickname
                    result[0].imageUrl shouldBe image1.url
                    result[1].nickname shouldBe latePlant.nickname
                    result[1].imageUrl shouldBe image4.url
                }
            }
        }
        describe("오래된 생성순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                        currentDate = CURRENT_DAY,
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY,
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY.minusDays(5),
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val latePlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY,
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY.minusDays(10),
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val image1 =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image2 =
                imageRepository.save(
                    Image(
                        url = "url2",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image3 =
                imageRepository.save(
                    Image(
                        url = "url3",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image4 =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )

            val image5 =
                imageRepository.save(
                    Image(
                        url = "url5",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = CURRENT_DAY,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.CreatedAsc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe latePlant.nickname
                    result[0].imageUrl shouldBe image4.url
                    result[1].nickname shouldBe recentPlant.nickname
                    result[1].imageUrl shouldBe image1.url
                }
            }
        }
        describe("최근 물주기순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                        currentDate = CURRENT_DAY,
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY.minusDays(5),
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val latePlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY.minusDays(10),
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val image1 =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image2 =
                imageRepository.save(
                    Image(
                        url = "url2",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image3 =
                imageRepository.save(
                    Image(
                        url = "url3",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image4 =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )

            val image5 =
                imageRepository.save(
                    Image(
                        url = "url5",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = CURRENT_DAY,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.WateredDesc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe recentPlant.nickname
                    result[0].imageUrl shouldBe image1.url
                    result[1].nickname shouldBe latePlant.nickname
                    result[1].imageUrl shouldBe image4.url
                }
            }
        }
        describe("오래된 물주기순 식물 전체 조회") {
            val location =
                locationRepository.save(
                    Location(
                        name = "장소명",
                        currentDate = CURRENT_DAY,
                    ),
                )

            val recentPlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명1",
                        nickname = "별명1",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY.minusDays(5),
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val latePlant =
                myPlantRepository.save(
                    MyPlant(
                        scientificName = "학명2",
                        nickname = "별명2",
                        startDate = CURRENT_DAY,
                        lastWateredDate = CURRENT_DAY.minusDays(10),
                        lastFertilizerDate = CURRENT_DAY,
                        alarm = Alarm(),
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setLocationRelation(location)
                    },
                )

            val image1 =
                imageRepository.save(
                    Image(
                        url = "url1",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image2 =
                imageRepository.save(
                    Image(
                        url = "url2",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image3 =
                imageRepository.save(
                    Image(
                        url = "url3",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(recentPlant)
                    },
                )

            val image4 =
                imageRepository.save(
                    Image(
                        url = "url4",
                        favorite = true,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )

            val image5 =
                imageRepository.save(
                    Image(
                        url = "url5",
                        favorite = false,
                        currentDate = CURRENT_DAY,
                    ).also {
                        it.setMyPlantRelation(latePlant)
                    },
                )
            context("식물 전체 조회를 하면") {
                val result =
                    myPlantService.findAllMyPlant(
                        now = CURRENT_DAY,
                        locationId = location.id,
                        sort = MyPlantQueryCreteria.WateredAsc,
                    )

                it("식물 정보와 인삿말과 imageUrl이 잘 조회된다.") {
                    result.size shouldBe 2
                    result[0].nickname shouldBe latePlant.nickname
                    result[0].imageUrl shouldBe image4.url
                    result[1].nickname shouldBe recentPlant.nickname
                    result[1].imageUrl shouldBe image1.url
                }
            }
        }
    }

    companion object {
        val CURRENT_DAY: LocalDate = LocalDate.of(2000, 5, 17)
    }
}
