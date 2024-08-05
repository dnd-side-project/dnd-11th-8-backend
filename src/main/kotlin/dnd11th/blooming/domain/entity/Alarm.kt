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
    var fertilizerAlarm: Boolean = false,
    @Column
    var fertilizerPeriod: Int? = null,
    @Column(nullable = false)
    var healthCheckAlarm: Boolean = false,
)
