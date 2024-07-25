package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.PlantSaveRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity
class Plant(
    @Column(nullable = false)
    var scientificName: String,
    @Column
    var name: String,
    @Column
    var startDate: LocalDate = LocalDate.now(),
    @Column
    var lastWateredDate: LocalDate = LocalDate.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToOne(fetch = FetchType.LAZY)
    private var alarm: Alarm? = null

    fun setAlarm(alarm: Alarm?) {
        this.alarm = alarm
    }

    fun getAlarm(): Alarm? = alarm

    companion object {
        fun from(
            request: PlantSaveRequest,
            alarm: Alarm? = null,
        ): Plant =
            Plant(
                scientificName = request.scientificName,
                name = request.name,
                startDate = request.startDate,
                lastWateredDate = request.lastWateredDate,
            ).apply { setAlarm(alarm) }
    }
}
