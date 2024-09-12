package dnd11th.blooming.domain.repository.myplant

import dnd11th.blooming.batch.UserPlantDto
import dnd11th.blooming.domain.entity.user.AlarmTime

interface MyPlantQueryDslRepository {
    fun findPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>
}
