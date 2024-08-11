package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Image(
    @Column
    var url: String,
    @Column
    var favorite: Boolean,
    currentDate: LocalDate = LocalDate.now(),
) : BaseEntity(currentDate) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myplant_id")
    var myPlant: MyPlant? = null
}
