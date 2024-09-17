package dnd11th.blooming.core.repository.myplant

import dnd11th.blooming.core.entity.myplant.UserPlantDto
import dnd11th.blooming.core.entity.user.AlarmTime

interface MyPlantQueryDslRepository {
    fun findPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>
}
