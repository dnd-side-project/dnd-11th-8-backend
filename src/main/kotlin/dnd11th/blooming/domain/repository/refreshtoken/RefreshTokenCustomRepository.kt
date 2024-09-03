package dnd11th.blooming.domain.repository.refreshtoken

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.RedisException
import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenCustomRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : RefreshTokenRepository {
    override fun findByToken(token: String): RefreshToken {
        val key = makeKey(token)
        return redisTemplate.opsForValue().get(key) as? RefreshToken
            ?: throw RedisException(ErrorType.NOT_FOUND_REDIS_KEY)
    }

    override fun save(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        redisTemplate.opsForValue().set(key, refreshToken.token, refreshToken.expiration, TimeUnit.SECONDS)
    }

    override fun update(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, refreshToken, refreshToken.expiration, TimeUnit.SECONDS)
        } else {
            throw RedisException(ErrorType.NOT_FOUND_REDIS_KEY)
        }
    }

    override fun delete(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        redisTemplate.delete(key)
    }

    private fun makeKey(token: String): String {
        return "refreshToken:$token"
    }
}
