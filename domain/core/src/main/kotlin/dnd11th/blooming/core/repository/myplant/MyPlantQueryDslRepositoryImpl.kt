package dnd11th.blooming.core.repository.myplant

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.core.entity.myplant.QMyPlant
import dnd11th.blooming.core.entity.myplant.UserPlantDto
import dnd11th.blooming.core.entity.user.AlarmTime
import dnd11th.blooming.core.entity.user.QUser
import org.springframework.stereotype.Repository

@Repository
class MyPlantQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MyPlantQueryDslRepository {

    companion object {
        const val HEALTH_CHECK_PERIOD_DAYS = 14
    }
    private val myPlant = QMyPlant.myPlant
    private val user = QUser.user

    override fun findNeedWaterPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    UserPlantDto::class.java,
                    user.id,
                    user.email,
                    user.deviceToken,
                    myPlant.id,
                    myPlant.nickname,
                    myPlant.lastWateredDate,
                    myPlant.alarm.waterPeriod,
                ),
            )
            .from(myPlant)
            .join(myPlant.user, user)
            .where(
                user.alarmStatus.isTrue,
                user.alarmTime.eq(alarmTime),
                user.deviceToken.isNotNull,
                myPlant.alarm.waterAlarm.isTrue,
                Expressions.numberTemplate(
                    Int::class.java,
                    "DATEDIFF(CURRENT_DATE, {0})",
                    myPlant.lastWateredDate,
                )
                    .eq(myPlant.alarm.waterPeriod),
            )
            .fetch()
    }

    override fun findNeedFertilizerPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    UserPlantDto::class.java,
                    user.id,
                    user.email,
                    user.deviceToken,
                    myPlant.id,
                    myPlant.nickname,
                    myPlant.lastWateredDate,
                    myPlant.alarm.waterPeriod,
                ),
            )
            .from(myPlant)
            .join(myPlant.user, user)
            .where(
                user.alarmStatus.isTrue,
                user.alarmTime.eq(alarmTime),
                user.deviceToken.isNotNull,
                myPlant.alarm.fertilizerAlarm.isTrue,
                Expressions.numberTemplate(
                    Int::class.java,
                    "DATEDIFF(CURRENT_DATE, {0})",
                    myPlant.lastFertilizerDate,
                )
                    .eq(myPlant.alarm.fertilizerPeriod),
            )
            .fetch()
    }

    override fun findNeedHealthCheckPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto> {
        return queryFactory
            .select(
                Projections.constructor(
                    UserPlantDto::class.java,
                    user.id,
                    user.email,
                    user.deviceToken,
                    myPlant.id,
                    myPlant.nickname,
                    myPlant.lastWateredDate,
                    myPlant.alarm.waterPeriod,
                ),
            )
            .from(myPlant)
            .join(myPlant.user, user)
            .where(
                user.alarmStatus.isTrue,
                user.alarmTime.eq(alarmTime),
                user.deviceToken.isNotNull,
                myPlant.alarm.healthCheckAlarm.isTrue,
                Expressions.numberTemplate(
                    Int::class.java,
                    "DATEDIFF(CURRENT_DATE, {0})",
                    myPlant.lastHealthCheckDate,
                )
                    .eq(HEALTH_CHECK_PERIOD_DAYS),
            )
            .fetch()
    }
}
