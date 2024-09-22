package dnd11th.blooming.client.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["dnd11th.blooming"])
class FeignClientConfig {
    @Bean
    fun errorDecoder(): CustomErrorDecoder {
        return CustomErrorDecoder()
    }
}
