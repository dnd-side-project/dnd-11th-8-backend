package dnd11th.blooming.domain.repository.refreshtoken

import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken

interface RefreshTokenRepository {
    fun findByToken(token: String): RefreshToken

    fun save(refreshToken: RefreshToken)

    fun update(refreshToken: RefreshToken)

    fun delete(refreshToken: RefreshToken)
}
