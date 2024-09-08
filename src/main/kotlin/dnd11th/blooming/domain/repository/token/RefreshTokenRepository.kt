package dnd11th.blooming.domain.repository.token

import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken

interface RefreshTokenRepository {
    fun findByUserId(userId: Long): String?

    fun save(refreshToken: RefreshToken)

    fun deleteByUserId(userId: Long)
}
