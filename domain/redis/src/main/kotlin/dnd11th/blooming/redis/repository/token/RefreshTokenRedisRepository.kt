package dnd11th.blooming.redis.repository.token

import dnd11th.blooming.redis.entity.refreshtoken.RefreshToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : RefreshTokenRepository {
    override fun findByUserId(userId: Long): String? {
        val key = makeKey(userId)
        return redisTemplate.opsForValue().get(key) as? String
    }

    override fun save(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.userId)
        redisTemplate.opsForValue().set(key, refreshToken.token, refreshToken.expiration, TimeUnit.SECONDS)
    }

    override fun deleteByUserId(userId: Long) {
        val key = makeKey(userId)
        redisTemplate.delete(key)
    }

    private fun makeKey(userId: Long): String {
        return "refreshToken:$userId"
    }
}
