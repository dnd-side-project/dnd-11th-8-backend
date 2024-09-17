package dnd11th.blooming.batch

import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class BatchConfig {
    @Bean
    fun jobRegistryBeanPostProcessorRemover(): BeanDefinitionRegistryPostProcessor? {
        return BeanDefinitionRegistryPostProcessor {
                registry: BeanDefinitionRegistry ->
            registry.removeBeanDefinition("jobRegistryBeanPostProcessor")
        }
    }
}
