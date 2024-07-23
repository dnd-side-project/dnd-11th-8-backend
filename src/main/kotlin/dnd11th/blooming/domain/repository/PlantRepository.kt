package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Plant
import org.springframework.data.jpa.repository.JpaRepository

interface PlantRepository : JpaRepository<Plant, Long>
