package dnd11th.blooming.domain.entity.region

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Region(
    @Column
    val nx: Int,
    @Column
    val ny: Int,
    @Column
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
)
