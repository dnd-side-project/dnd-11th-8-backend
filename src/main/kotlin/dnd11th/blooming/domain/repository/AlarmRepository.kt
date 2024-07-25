package dnd11th.blooming.domain.repository

import dnd11th.blooming.domain.entity.Alarm
import org.springframework.data.jpa.repository.JpaRepository

interface AlarmRepository : JpaRepository<Alarm, Long>
