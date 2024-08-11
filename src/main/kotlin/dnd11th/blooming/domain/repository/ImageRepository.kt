package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
