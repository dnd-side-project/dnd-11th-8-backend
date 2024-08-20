package dnd11th.blooming.domain.repository.myplant

import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.QMyPlant
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
                myPlant.location.isNull.desc(),
                when (order) {
                    MyPlantQueryCreteria.CreatedDesc, MyPlantQueryCreteria.NoLocation -> myPlant.createdDate.desc()
                    MyPlantQueryCreteria.CreatedAsc -> myPlant.createdDate.asc()
                },
            )
            .fetch()
    }
}
