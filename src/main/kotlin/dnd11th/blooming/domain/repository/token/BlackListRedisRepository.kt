package dnd11th.blooming.domain.repository.token

import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class BlackListRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : BlackListRepository {
    override fun existsByToken(token: String): Boolean {
        val key = makeKey(token)
        return redisTemplate.hasKey(key)
    }

    override fun save(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        redisTemplate.opsForValue().set(key, refreshToken.token, refreshToken.expiration, TimeUnit.SECONDS)
    }

    private fun makeKey(token: String): String {
        return "blackList:$token"
    }
}
