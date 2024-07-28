package dnd11th.blooming.common.jwt

import dnd11th.blooming.domain.entity.UserClaims
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${auth.jwt.expiration}") private val expiration: Long,
    private val secretKey: SecretKey,
) {
    fun generateAccessToken(
        userId: Long,
        email: String,
        nickname: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiration)
        return Jwts.builder()
            .expiration(expiry)
            .claim("id", userId)
            .claim("email", email)
            .claim("nickname", nickname)
            .signWith(secretKey)
            .compact()
    }

    fun resolveAccessToken(token: String?): UserClaims {
        val claims = getClaims(token)
        return UserClaims(
            (claims["id"] as Number).toLong(),
            claims["email"] as String,
            claims["nickname"] as String,
        )
    }

    fun getClaims(token: String?): Claims {
        return try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw IllegalArgumentException()
        } catch (e: JwtException) {
            throw JwtException("Invalid Token {}")
        }
    }
}
