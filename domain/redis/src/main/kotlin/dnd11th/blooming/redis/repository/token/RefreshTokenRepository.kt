package dnd11th.blooming.redis.repository.token

import dnd11th.blooming.redis.entity.refreshtoken.RefreshToken

interface RefreshTokenRepository {
    fun findByUserId(userId: Long): String?

    fun save(refreshToken: RefreshToken)

    fun deleteByUserId(userId: Long)
}
