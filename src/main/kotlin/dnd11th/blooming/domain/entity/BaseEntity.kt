package dnd11th.blooming.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_date", updatable = false)
    var createdDate: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_date")
    var updatedDate: LocalDateTime = LocalDateTime.now()

    @PrePersist
    protected fun onCreate() {
        createdDate = LocalDateTime.now()
    }

    @PreUpdate
    protected fun onUpdate() {
        updatedDate = LocalDateTime.now()
    }
}
