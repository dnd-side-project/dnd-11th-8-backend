package dnd11th.blooming.api.dto.user

sealed class SocialLoginResponse {
    data class Success(
        val status: String = "success",
        val accessToken: String,
        val refreshToken: String,
    ) : SocialLoginResponse()

    data class Pending(
        val status: String = "pending",
        val registerToken: String,
    ) : SocialLoginResponse()
}
