package dnd11th.blooming.core.entity.myplant

import dnd11th.blooming.core.entity.common.BaseEntity
import dnd11th.blooming.core.entity.location.Location
import dnd11th.blooming.core.entity.plant.Plant
import dnd11th.blooming.core.entity.user.User
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
import java.time.Month
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
    var plantImageUrl: String? = null
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
        lastWateredDate: Int?,
        lastFertilizerDate: Int?,
        now: LocalDate,
    ) {
        nickname?.let { this.nickname = nickname }
        location?. let { this.location = location }
        startDate?.let { this.startDate = startDate }
        lastWateredDate?.let {
            this.lastWateredDate =
                selectLastDate(lastWateredDate, now)
        }
        lastFertilizerDate?.let {
            this.lastFertilizerDate =
                selectLastDate(lastFertilizerDate, now)
        }
    }

    fun modifyAlarm(
        dto: AlarmModifyDto,
        month: Month,
    ) {
        this.alarm = Alarm.createAlarm(dto, plant, month)
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
        fun create(
            dto: MyPlantCreateDto,
            location: Location?,
            plant: Plant?,
            user: User,
            now: LocalDate,
        ): MyPlant =
            MyPlant(
                // plant가 없으면 scientificName 사용
                scientificName = plant?.korName ?: dto.scientificName,
                // nickname이 없으면 scientificName 사용
                nickname = dto.nickname ?: dto.scientificName,
                startDate = dto.startDate,
                lastWateredDate =
                    selectLastDate(
                        dto.lastWateredDate,
                        now,
                    ),
                lastFertilizerDate =
                    selectLastDate(
                        dto.lastFertilizerDate,
                        now,
                    ),
                lastHealthCheckDate = LocalDate.now(),
                alarm = Alarm.createAlarm(dto, plant, now.month),
            ).also {
                it.location = location
                it.plant = plant
                it.user = user
                plant?.let { plant -> it.plantImageUrl = plant.imageUrl }
            }

        private fun selectLastDate(
            number: Int,
            now: LocalDate,
        ) = when (number) {
            1 -> now
            2 -> now.minusDays(1)
            3 -> now.minusDays(3)
            4 -> now.minusDays(7)
            5 -> now.minusDays(14)
            else -> null
        }
    }

    override fun toString(): String {
        return "MyPlant(" +
            "id=$id, " +
            "scientificName='$scientificName', " +
            "nickname='$nickname', " +
            "startDate=$startDate, " +
            "lastWateredDate=$lastWateredDate, " +
            "lastFertilizerDate=$lastFertilizerDate, " +
            "lastHealthCheckDate=$lastHealthCheckDate, " +
            "plantImageUrl='$plantImageUrl', " +
            "user=${user?.id}, " +
            "plant=${plant?.id}, " +
            "location=${location?.id}" +
            ")"
    }
}
