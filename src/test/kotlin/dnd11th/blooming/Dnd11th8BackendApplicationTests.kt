package dnd11th.blooming

import dnd11th.blooming.client.config.FcmConfig
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class Dnd11th8BackendApplicationTests {
    @TestConfiguration
    class TestConfig {
        @Bean
        fun fcmConfig(): FcmConfig {
            return mockk(relaxed = true)
        }
    }

    @Test
    fun contextLoads() {
    }
}
