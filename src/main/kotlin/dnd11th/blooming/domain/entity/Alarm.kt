package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.PlantSaveRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Alarm(
    @Column(nullable = false)
    var waterAlarm: Boolean,
    @Column
    var waterPeriod: Int,
    @Column(nullable = false)
    var nutrientsAlarm: Boolean,
    @Column
    var nutrientsPeriod: Int,
    @Column(nullable = false)
    var repotAlarm: Boolean,
    @Column
    var repotPeriod: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    companion object {
        fun from(request: PlantSaveRequest): Alarm =
            Alarm(
                waterAlarm = request.waterAlarm ?: false,
                waterPeriod = request.waterPeriod ?: 3,
                nutrientsAlarm = request.nutrientsAlarm ?: false,
                nutrientsPeriod = request.nutrientsPeriod ?: 30,
                repotAlarm = request.repotAlarm ?: false,
                repotPeriod = request.repotPeriod ?: 60,
            )
    }
}
