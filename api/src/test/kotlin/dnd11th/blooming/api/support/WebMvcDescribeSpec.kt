package dnd11th.blooming.api.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dnd11th.blooming.api.config.WebMvcConfig
import dnd11th.blooming.api.controller.home.HomeController
import dnd11th.blooming.api.controller.image.ImageController
import dnd11th.blooming.api.controller.location.LocationController
import dnd11th.blooming.api.controller.myplant.MyPlantController
import dnd11th.blooming.api.interceptor.AuthInterceptor
import dnd11th.blooming.api.resolver.LoginUserArgumentResolver
import dnd11th.blooming.api.resolver.PendingUserArgumentResolver
import dnd11th.blooming.api.service.home.HomeService
import dnd11th.blooming.api.service.image.ImageService
import dnd11th.blooming.api.service.location.LocationService
import dnd11th.blooming.api.service.myplant.MyPlantService
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(
    value = [
        HomeController::class,
        LocationController::class,
        MyPlantController::class,
        ImageController::class,
    ],
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = [
                WebMvcConfig::class, AuthInterceptor::class,
                LoginUserArgumentResolver::class, PendingUserArgumentResolver::class,
            ],
        ),
    ],
)
@Import(TestWebMvcConfig::class)
@ActiveProfiles("test")
abstract class WebMvcDescribeSpec : DescribeSpec() {
    @Autowired
    protected lateinit var context: WebApplicationContext

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @MockkBean
    protected lateinit var homeService: HomeService

    @MockkBean
    protected lateinit var imageService: ImageService

    @MockkBean
    protected lateinit var myPlantService: MyPlantService

    @MockkBean
    protected lateinit var locationService: LocationService

    @BeforeEach
    fun setUp() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }
}
