package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Alarm(
    @Column(nullable = false)
    var waterAlarm: Boolean = false,
    @Column
    var waterPeriod: Int? = null,
    @Column(nullable = false)
    var nutrientsAlarm: Boolean = false,
    @Column
    var nutrientsPeriod: Int? = null,
    @Column(nullable = false)
    var repotAlarm: Boolean = false,
    @Column
    var repotPeriod: Int? = null,
)
