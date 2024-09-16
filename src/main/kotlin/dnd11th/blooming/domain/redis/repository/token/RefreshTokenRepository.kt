package dnd11th.blooming.domain.redis.repository.token

import dnd11th.blooming.domain.redis.entity.refreshtoken.RefreshToken

interface RefreshTokenRepository {
    fun findByUserId(userId: Long): String?

    fun save(refreshToken: RefreshToken)

    fun deleteByUserId(userId: Long)
}
