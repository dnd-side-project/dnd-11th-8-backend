package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long>
