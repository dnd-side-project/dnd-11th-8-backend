package dnd11th.blooming.core.repository.myplant

import dnd11th.blooming.core.entity.myplant.UserPlantDto
import dnd11th.blooming.core.entity.user.AlarmTime

interface MyPlantQueryDslRepository {
    fun findNeedWaterPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>

    fun findNeedFertilizerPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>

    fun findNeedHealthCheckPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto>

}
