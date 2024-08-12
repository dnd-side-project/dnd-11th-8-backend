package dnd11th.blooming.common.jwt

import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.RegisterClaims
import dnd11th.blooming.domain.entity.user.UserClaims
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
) {
    fun generateRegisterToken(
        email: String,
        provider: OauthProvider,
    ): String {
        return generateRegisterToken(email, provider.name, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateAccessToken(
        userId: Long?,
        email: String,
        nickname: String,
    ): String {
        return generateToken(userId, email, nickname, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateRefreshToken(
        userId: Long?,
        email: String,
        nickname: String,
    ): String {
        return generateToken(userId, email, nickname, jwtProperties.refresh.expiry, jwtProperties.refresh.secret)
    }

    fun resolveRegisterToken(
        token: String?,
        secret: String,
    ): RegisterClaims {
        val secretKey: SecretKey = getSecretKey(secret)
        val claims = getClaims(token, secretKey)
        return RegisterClaims(
            claims["email"] as String,
            claims["provider"] as String,
        )
    }

    fun resolveAccessToken(token: String?): UserClaims {
        return resolveToken(token, jwtProperties.access.secret)
    }

    fun resolveRefreshToken(token: String?): UserClaims {
        return resolveToken(token, jwtProperties.refresh.secret)
    }

    private fun generateRegisterToken(
        email: String,
        provider: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("email", email)
            .claim("provider", provider)
            .signWith(getSecretKey(secret))
            .compact()
    }

    private fun generateToken(
        userId: Long?,
        email: String,
        nickname: String,
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("id", userId)
            .claim("email", email)
            .claim("nickname", nickname)
            .signWith(getSecretKey(secret))
            .compact()
    }

    private fun resolveToken(
        token: String?,
        secret: String,
    ): UserClaims {
        val secretKey: SecretKey = getSecretKey(secret)
        val claims = getClaims(token, secretKey)
        return UserClaims(
            (claims["id"] as Number).toLong(),
            claims["email"] as String,
            claims["nickname"] as String,
        )
    }

    fun getClaims(
        token: String?,
        key: SecretKey,
    ): Claims {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw IllegalArgumentException()
        } catch (e: JwtException) {
            throw JwtException("Invalid Token {}")
        }
    }

    private fun getSecretKey(secret: String): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}
