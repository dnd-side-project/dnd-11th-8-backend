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
    override fun findPlantsByAlarmTimeInBatch(alarmTime: AlarmTime): List<UserPlantDto> {
        val myPlant = QMyPlant.myPlant
        val user = QUser.user
        return queryFactory
            .select(
                Projections.constructor(
                    UserPlantDto::class.java,
                    user.id,
                    user.email,
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
}
