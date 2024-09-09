package dnd11th.blooming.domain.repository.myplant

import dnd11th.blooming.api.dto.myplant.MyPlantQueryCreteria
import dnd11th.blooming.domain.entity.Location
import dnd11th.blooming.domain.entity.MyPlant
import dnd11th.blooming.domain.entity.user.User

interface MyPlantQueryDslRepository {
    fun findAllByLocationAndUserOrderBy(
        location: Location?,
        user: User,
        order: MyPlantQueryCreteria,
    ): List<MyPlant>
}
