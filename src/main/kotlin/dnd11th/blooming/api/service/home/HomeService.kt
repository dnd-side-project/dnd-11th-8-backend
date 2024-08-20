package dnd11th.blooming.api.service.home

import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.api.dto.home.MyPlantHomeResponse
import dnd11th.blooming.api.service.myplant.MyPlantMessageFactory
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HomeService(
    private val myPlantRepository: MyPlantRepository,
    private val myPlantMessageFactory: MyPlantMessageFactory,
) {
    fun getHome(
        user: User,
        now: LocalDate,
    ): HomeResponse {
        val myPlantHomeResponses =
            myPlantRepository.findAllByUser(user).stream()
                .map { myPlant -> MyPlantHomeResponse.of(myPlant, now) }
                .toList()
        val message = myPlantMessageFactory.createGreetingMessage(user.nickname)

        return HomeResponse.from(message, myPlantHomeResponses)
    }
}
