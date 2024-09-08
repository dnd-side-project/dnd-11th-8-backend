package dnd11th.blooming.domain.repository.myplant

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.batch.UserPlantDto
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.QMyPlant
import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.QUser
import dnd11th.blooming.domain.entity.user.User
import org.springframework.stereotype.Repository

@Repository
class MyPlantQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MyPlantQueryDslRepository {
    override fun findAllByLocationAndUserOrderBy(
        location: Location?,
        user: User,
        order: MyPlantQueryCreteria,
    ): List<MyPlant> {
        val myPlant = QMyPlant.myPlant

        return queryFactory
            .selectFrom(myPlant)
            .where(
                myPlant.user.eq(user),
                when (order) {
                    MyPlantQueryCreteria.NoLocation -> myPlant.location.isNull
                    else -> location?.let { myPlant.location.eq(it) }
                },
            )
            .orderBy(
                when (order) {
                    MyPlantQueryCreteria.CreatedDesc -> myPlant.createdDate.desc()
                    MyPlantQueryCreteria.CreatedAsc -> myPlant.createdDate.asc()
                    else -> myPlant.createdDate.desc()
                }.nullsFirst(),
            )
            .fetch()
    }

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
