package dnd11th.blooming.api.config

import dnd11th.blooming.api.config.properties.CorsProperties
import dnd11th.blooming.common.interceptor.AuthInterceptor
import dnd11th.blooming.common.resolver.LoginUserArgumentResolver
import dnd11th.blooming.common.resolver.PendingUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val authInterceptor: AuthInterceptor,
    private val loginUserArgumentResolver: LoginUserArgumentResolver,
    private val pendingUserArgumentResolver: PendingUserArgumentResolver,
    private val corsProperties: CorsProperties,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authInterceptor)
        super.addInterceptors(registry)
    }

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver?>) {
        argumentResolvers.add(loginUserArgumentResolver)
        argumentResolvers.add(pendingUserArgumentResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(*corsProperties.allowedOrigin.toTypedArray())
            .allowedHeaders("*")
            .allowedMethods("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .maxAge(corsProperties.maxAge)
    }
}
