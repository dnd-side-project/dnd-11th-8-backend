package dnd11th.blooming.domain.repository.token

import dnd11th.blooming.domain.entity.refreshtoken.RefreshToken

interface BlackListRepository {
    fun existsByToken(token: String): Boolean

    fun save(refreshToken: RefreshToken)
}
