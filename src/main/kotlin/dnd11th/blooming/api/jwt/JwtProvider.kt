package dnd11th.blooming.api.jwt

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.domain.core.entity.user.OauthProvider
import dnd11th.blooming.domain.core.entity.user.RegisterClaims
import dnd11th.blooming.domain.core.entity.user.UserClaims
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
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
        return generateToken(email, provider.name, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateAccessToken(
        userId: Long?,
        email: String,
    ): String {
        return generateToken(userId, email, jwtProperties.access.expiry, jwtProperties.access.secret)
    }

    fun generateRefreshToken(
        userId: Long?,
        email: String,
    ): String {
        return generateToken(userId, email, jwtProperties.refresh.expiry, jwtProperties.refresh.secret)
    }

    fun resolveRegisterToken(token: String?): RegisterClaims {
        val secretKey: SecretKey = getSecretKey(jwtProperties.access.secret)
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

    fun getRefreshTokenExpiration(token: String): Long {
        val secretKey: SecretKey = getSecretKey(jwtProperties.refresh.secret)
        val claims: Claims = getClaims(token, secretKey)
        return claims["exp"] as Long
    }

    fun getExpiredIn(): Long {
        val expiryInMillis: Long = jwtProperties.access.expiry
        return expiryInMillis / 1000
    }

    fun getRefreshExpiredIn(): Long {
        val expiryInMillis: Long = jwtProperties.refresh.expiry
        return expiryInMillis / 1000
    }

    private fun generateToken(
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
        expiryTime: Long,
        secret: String,
    ): String {
        val now = Date()
        val expiry = Date(now.time + expiryTime)
        return Jwts.builder()
            .expiration(expiry)
            .claim("id", userId)
            .claim("email", email)
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
        )
    }

    fun getClaims(
        token: String?,
        key: SecretKey,
    ): Claims {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw UnAuthorizedException(ErrorType.TOKEN_EXPIRED)
        } catch (e: Exception) {
            throw UnAuthorizedException(ErrorType.INVALID_JWT_TOKEN)
        }
    }

    private fun getSecretKey(secret: String): SecretKey {
        return Keys.hmacShaKeyFor(secret.toByteArray())
    }
}
