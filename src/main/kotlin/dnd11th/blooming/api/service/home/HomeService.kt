package dnd11th.blooming.api.service.home

import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.api.dto.home.MyPlantHomeResponse
import dnd11th.blooming.api.service.myplant.MyPlantMessageFactory
import dnd11th.blooming.domain.repository.MyPlantRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HomeService(
    private val myPlantRepository: MyPlantRepository,
    private val myPlantMessageFactory: MyPlantMessageFactory,
) {
    fun getHome(now: LocalDate): HomeResponse {
        val myPlantHomeResponses =
            myPlantRepository.findAll().stream()
                .map { myPlant -> MyPlantHomeResponse.of(myPlant, now) }
                .toList()
        // TODO : username 찾아와야함
        val message = myPlantMessageFactory.createGreetingMessage("테스트유저")

        return HomeResponse.from(message, myPlantHomeResponses)
    }
}
