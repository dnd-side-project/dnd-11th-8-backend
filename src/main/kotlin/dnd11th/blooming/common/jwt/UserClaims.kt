package dnd11th.blooming.common.jwt

data class UserClaims(
    val id: Long,
    val email: String,
    val nickname: String,
)
