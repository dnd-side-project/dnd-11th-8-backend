package dnd11th.blooming.domain.entity.plant

import dnd11th.blooming.domain.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Plant(
    @Column
    var korName: String,
    @Column
    var engName: String,
    @Column
    @Enumerated(EnumType.STRING)
    var springSummerFallWater: Water,
    @Column
    @Enumerated(EnumType.STRING)
    var winterWater: Water,
    @Column
    var growHeight: Int,
    @Column
    var growWidth: Int,
    @Column
    @Enumerated(EnumType.STRING)
    var light: Light,
    @Column
    @Enumerated(EnumType.STRING)
    var difficulty: Difficulty,
    @Column
    @Enumerated(EnumType.STRING)
    var growTemperature: GrowTemperature,
    @Column
    @Enumerated(EnumType.STRING)
    var lowestTemperature: LowestTemperature,
    @Column
    @Enumerated(EnumType.STRING)
    var toxicity: Toxicity,
    @Column
    @Enumerated(EnumType.STRING)
    var fertilizer: Fertilizer,
    @Column
    @Enumerated(EnumType.STRING)
    var humidity: Humidity,
    @Column
    var pests: String,
    @Column
    var location: String,
    @Column
    var imageUrl: String,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
