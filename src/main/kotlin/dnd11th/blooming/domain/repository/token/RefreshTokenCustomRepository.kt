package dnd11th.blooming.domain.repository.token

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
    override fun findByToken(token: String): RefreshToken? {
        val key = makeKey(token)
        return redisTemplate.opsForValue().get(key) as? RefreshToken
    }

    override fun save(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        redisTemplate.opsForValue().set(key, refreshToken.token, refreshToken.expiration, TimeUnit.SECONDS)
    }

    override fun delete(refreshToken: RefreshToken) {
        val key = makeKey(refreshToken.token)
        redisTemplate.delete(key)
    }

    private fun makeKey(token: String): String {
        return "refreshToken:$token"
    }
}
