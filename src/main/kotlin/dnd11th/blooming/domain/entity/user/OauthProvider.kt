package dnd11th.blooming.domain.entity.user

import dnd11th.blooming.common.exception.BadRequestException
import dnd11th.blooming.common.exception.ErrorType
import java.lang.Exception

enum class OauthProvider {
    KAKAO,
    APPLE,
    ;

    companion object {
        fun from(provider: String): OauthProvider {
            return try {
                valueOf(provider.uppercase())
            } catch (e: Exception) {
                throw BadRequestException(ErrorType.INVALID_OAUTH_PROVIDER)
            }
        }
    }
}
