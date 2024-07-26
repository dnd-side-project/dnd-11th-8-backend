package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.MyPlant
import org.springframework.data.jpa.repository.JpaRepository

interface MyPlantRepository : JpaRepository<MyPlant, Long>
