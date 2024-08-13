package dnd11th.blooming.domain.entity.region

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class Region(
    @Column
    val nx: Int,
    @Column
    val ny: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctp_id")
    val cityProvince: CityProvince? = null,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sig_id")
    val siGunGu: SiGunGu? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
