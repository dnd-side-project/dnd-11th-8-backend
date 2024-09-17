package dnd11th.blooming.redis.entity.refreshtoken

data class RefreshToken(
    val userId: Long,
    val token: String,
    val expiration: Long,
) {
    companion object {
        fun create(
            userId: Long,
            token: String,
            expiration: Long,
        ): RefreshToken {
            return RefreshToken(userId, token, expiration)
        }
    }
}
