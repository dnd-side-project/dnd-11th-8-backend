package dnd11th.blooming.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
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
    @Column
    var waterAlarm: Boolean = true,
    @Column
    var nutrientsAlarm: Boolean = true,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
