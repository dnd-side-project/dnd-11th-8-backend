package dnd11th.blooming.support

import dnd11th.blooming.client.config.FcmConfig
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("test")
class MockFcmConfig {
    @Bean
    fun fcmConfig(): FcmConfig {
        return mockk(relaxed = true)
    }
}
