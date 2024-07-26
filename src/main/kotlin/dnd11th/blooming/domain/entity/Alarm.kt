package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Alarm(
    @Column(nullable = false)
    var waterAlarm: Boolean,
    @Column(nullable = false)
    var waterPeriod: Int,
    @Column(nullable = false)
    var nutrientsAlarm: Boolean,
    @Column(nullable = false)
    var nutrientsPeriod: Int,
    @Column(nullable = false)
    var repotAlarm: Boolean,
    @Column(nullable = false)
    var repotPeriod: Int,
)
