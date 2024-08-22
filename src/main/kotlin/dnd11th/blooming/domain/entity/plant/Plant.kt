package dnd11th.blooming.domain.entity.plant

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
    var springWater: Water,
    @Column
    @Enumerated(EnumType.STRING)
    var summerWater: Water,
    @Column
    @Enumerated(EnumType.STRING)
    var fallWater: Water,
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
    var location: GrowLocation,
    @Column
    var imageUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
