package dnd11th.blooming.api.service.user

import dnd11th.blooming.domain.entity.devicetoken.DeviceToken
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.repository.devicetoken.DeviceTokenRepository
import org.springframework.stereotype.Service

@Service
class DeviceTokenService(
    private val deviceTokenRepository: DeviceTokenRepository,
) {
    fun saveToken(
        loginUser: User,
        token: String,
    ) {
        val newToken: DeviceToken = DeviceToken(userId = loginUser.id!!, token = token)
        deviceTokenRepository.save(newToken)
    }

    fun invalidToken(
        loginUser: User,
        token: String,
    ) {
        val deviceToken: DeviceToken =
            deviceTokenRepository.findByTokenAndUserId(token, loginUser.id!!)
                ?: throw IllegalArgumentException("device token invalid")
        deviceToken.invalidToken()
    }
}
