package dnd11th.blooming.domain.entity.user

data class OidcUser(
    val sub: String,
    val email: String,
    val nickname: String,
)
