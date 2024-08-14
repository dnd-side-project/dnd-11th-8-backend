package dnd11th.blooming.domain.entity

import dnd11th.blooming.api.dto.image.ImageCreateDto
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
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myplant_id")
    var myPlant: MyPlant? = null

    fun modifyFavorite(favorite: Boolean) {
        this.favorite = favorite
    }

    companion object {
        fun createImage(
            dto: ImageCreateDto,
            myPlant: MyPlant,
            now: LocalDate,
        ) = Image(
            url = dto.url,
            favorite = false,
            currentDate = now,
        ).also {
            it.myPlant = myPlant
        }
    }
}
