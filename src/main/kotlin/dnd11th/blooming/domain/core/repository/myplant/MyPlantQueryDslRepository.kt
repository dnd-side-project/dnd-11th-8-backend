package dnd11th.blooming.domain.core.repository.myplant

import dnd11th.blooming.batch.UserPlantDto
import dnd11th.blooming.domain.core.entity.user.AlarmTime

interface MyPlantQueryDslRepository {
    fun findPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>
}
