package dnd11th.blooming.common.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtConfig(
    @Value("\${auth.jwt.secret}")
    private val secret: String,
) {
    @Bean
    fun secretKey(): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}
