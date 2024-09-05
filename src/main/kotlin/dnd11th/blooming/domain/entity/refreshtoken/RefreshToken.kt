package dnd11th.blooming.domain.entity.refreshtoken

import java.time.Instant

data class RefreshToken(
    val token: String,
    val userId: Long,
    val expiration: Long,
) {
    companion object {
        fun create(
            token: String,
            userId: Long,
            now: Instant,
            expiry: Long,
        ): RefreshToken {
            return RefreshToken(token, userId, expiry - now.epochSecond)
        }
    }
}
