package dnd11th.blooming.domain.entity

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
    var createdDate: LocalDate = LocalDate.now(),
    @Column
    var startDate: LocalDate = LocalDate.now(),
    @Column
    var lastWateredDate: LocalDate = LocalDate.now(),
    @Column
    var lastFertilizerDate: LocalDate = LocalDate.now(),
    @Embedded
    var alarm: Alarm,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null

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

    fun getWaterRemainDay(now: LocalDate): Int? {
        if (!alarm.waterAlarm) return null

        return alarm.waterPeriod?.let { waterPeriod ->
            Period.between(now, lastWateredDate).days + waterPeriod
        }
    }

    fun getFerilizerRemainDate(now: LocalDate): Int? {
        if (!alarm.fertilizerAlarm) return null

        return alarm.fertilizerPeriod?.let { fertilizerPeriod ->
            Period.between(now, lastFertilizerDate).days + fertilizerPeriod
        }
    }

    fun getLocationName(): String? {
        return location?.let { location ->
            location.name
        }
    }

    fun doWater(now: LocalDate) {
        lastWateredDate = now
    }

    fun doFertilizer(now: LocalDate) {
        lastFertilizerDate = now
    }

    fun modifyHealthCheck(healthCheckAlarm: Boolean) {
        this.alarm.healthCheckAlarm = healthCheckAlarm
    }
}
