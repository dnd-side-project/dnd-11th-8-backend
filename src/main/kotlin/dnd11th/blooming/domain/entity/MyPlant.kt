package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.myplant.MyPlantCreateDto
import dnd11th.blooming.domain.entity.plant.Plant
import dnd11th.blooming.domain.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate
import java.time.Period

@Entity
class MyPlant(
    @Column(nullable = false)
    var scientificName: String,
    @Column
    var nickname: String,
    @Column
    var startDate: LocalDate,
    @Column
    var lastWateredDate: LocalDate?,
    @Column
    var lastFertilizerDate: LocalDate?,
    @Column
    var lastHealthCheckDate: LocalDate,
    @Embedded
    var alarm: Alarm,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var plantImageUrl: String = "블루밍 대표 이미지"
    // TODO : 블루밍 대표 이미지 넣기

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    var plant: Plant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null

    fun getLocationName(): String? {
        return location?.name
    }

    fun getDateSinceLastWater(now: LocalDate): Int? {
        return lastWateredDate?.let { Period.between(lastWateredDate, now).days }
    }

    fun getDateSinceLastFertilizer(now: LocalDate): Int? {
        return lastFertilizerDate?.let { Period.between(lastFertilizerDate, now).days }
    }

    fun getDateSinceLastHealthCheck(now: LocalDate): Int {
        return Period.between(lastHealthCheckDate, now).days
    }

    fun modify(
        nickname: String?,
        location: Location?,
        startDate: LocalDate?,
        lastWateredDate: LocalDate?,
        lastFertilizerDate: LocalDate?,
    ) {
        nickname?.let { this.nickname = nickname }
        location?. let { this.location = location }
        startDate?.let { this.startDate = startDate }
        lastWateredDate?.let { this.lastWateredDate = lastWateredDate }
        lastFertilizerDate?.let { this.lastFertilizerDate = lastFertilizerDate }
    }

    fun modifyAlarm(alarm: Alarm) {
        this.alarm = alarm
    }

    fun doWater(now: LocalDate) {
        lastWateredDate = now
    }

    fun doFertilizer(now: LocalDate) {
        lastFertilizerDate = now
    }

    fun doHealthCheck(now: LocalDate) {
        lastHealthCheckDate = now
    }

    companion object {
        fun createMyPlant(
            dto: MyPlantCreateDto,
            location: Location?,
            plant: Plant?,
            user: User,
        ): MyPlant =
            MyPlant(
                // plant가 없으면 scientificName 사용
                scientificName = plant?.korName ?: dto.scientificName,
                // nickname이 없으면 scientificName 사용
                nickname = dto.nickname ?: dto.scientificName,
                startDate = dto.startDate,
                lastWateredDate = dto.lastWateredDate,
                lastFertilizerDate = dto.lastFertilizerDate,
                lastHealthCheckDate = LocalDate.now(),
                alarm =
                    Alarm(
                        waterAlarm = dto.waterAlarm,
                        waterPeriod = dto.waterPeriod,
                        fertilizerAlarm = dto.fertilizerAlarm,
                        fertilizerPeriod = dto.fertilizerPeriod,
                        healthCheckAlarm = dto.healthCheckAlarm,
                    ),
            ).also {
                it.location = location
                it.plant = plant
                it.user = user
                plant?.let { plant -> it.plantImageUrl = plant.imageUrl }
            }
    }
}
