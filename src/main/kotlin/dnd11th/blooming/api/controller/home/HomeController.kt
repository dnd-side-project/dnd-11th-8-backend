package dnd11th.blooming.api.controller.home

import dnd11th.blooming.api.dto.home.HomeResponse
import dnd11th.blooming.api.service.home.HomeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/home")
class HomeController(
    private val homeService: HomeService,
) : HomeApi {
    @GetMapping
    override fun getHome(): HomeResponse = homeService.getHome(LocalDate.now())
}
