package dnd11th.blooming.domain.repository.myplant

import com.querydsl.jpa.impl.JPAQueryFactory
import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.QMyPlant
import dnd11th.blooming.domain.entity.user.User
import org.springframework.stereotype.Repository

@Repository
class MyPlantCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MyPlantCustomRepository {
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
                location?.let { myPlant.location.eq(it) },
            )
            .orderBy(
                when (order) {
                    MyPlantQueryCreteria.CreatedDesc -> myPlant.createdDate.desc()
                    MyPlantQueryCreteria.CreatedAsc -> myPlant.createdDate.asc()
                    MyPlantQueryCreteria.WateredDesc -> myPlant.lastWateredDate.desc()
                    MyPlantQueryCreteria.WateredAsc -> myPlant.lastWateredDate.asc()
                },
            )
            .fetch()
    }
}
