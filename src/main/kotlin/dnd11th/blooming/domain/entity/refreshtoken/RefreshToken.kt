package dnd11th.blooming.domain.entity.refreshtoken

data class RefreshToken(
    val token: String,
    val userId: Long,
    val expiration: Long,
    var isBlackList: Boolean = false,
    var isUsed: Boolean = false,
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

    fun use() {
        isUsed = true
    }

    fun blackList() {
        isBlackList = true
    }

    fun checkInValid(): Boolean {
        return isBlackList || isUsed
    }
}
