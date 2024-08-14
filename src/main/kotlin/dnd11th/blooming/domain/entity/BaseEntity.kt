package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDate

@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_date", updatable = false)
    var createdDate: LocalDate = LocalDate.now()

    @Column(name = "updated_date")
    var updatedDate: LocalDate = LocalDate.now()

    @PrePersist
    protected fun onCreate() {
        createdDate = LocalDate.now()
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedDate = LocalDate.now()
    }
}
