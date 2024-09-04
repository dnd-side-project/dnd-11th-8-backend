package dnd11th.blooming.domain.entity.refreshtoken

data class RefreshToken(
    val token: String,
    val userId: Long,
    val expiration: Long,
) {
    companion object {
        fun create(
            token: String,
            userId: Long,
            expiration: Long,
        ): RefreshToken {
            return RefreshToken(token, userId, expiration)
        }
    }
}
