package dnd11th.blooming.service

import dnd11th.blooming.domain.Plant
import org.springframework.data.jpa.repository.JpaRepository

interface PlantRepository : JpaRepository<Plant, Long>
