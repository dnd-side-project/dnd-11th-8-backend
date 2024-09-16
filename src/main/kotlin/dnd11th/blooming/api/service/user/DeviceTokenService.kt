package dnd11th.blooming.api.service.user

import dnd11th.blooming.domain.core.entity.user.User
import dnd11th.blooming.domain.core.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeviceTokenService(
    private val userRepository: UserRepository,
) {
    fun saveToken(
        loginUser: User,
        token: String,
    ) {
        loginUser.updateDeviceToken(token)
        userRepository.save(loginUser)
    }

    fun invalidToken(loginUser: User) {
        loginUser.invalidDeviceToken()
        userRepository.save(loginUser)
    }
}
