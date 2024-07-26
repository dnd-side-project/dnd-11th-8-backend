package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
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
    var nickname: String,
    @Column
    var startDate: LocalDate = LocalDate.now(),
    @Column
    var lastWateredDate: LocalDate = LocalDate.now(),
    @Embedded
    var alarm: Alarm,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
