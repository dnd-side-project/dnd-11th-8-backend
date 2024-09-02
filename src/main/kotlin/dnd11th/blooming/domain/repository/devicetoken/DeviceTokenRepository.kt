package dnd11th.blooming.domain.repository.devicetoken

import dnd11th.blooming.domain.entity.devicetoken.DeviceToken
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenRepository : JpaRepository<DeviceToken, Long> {
    fun findByToken(token: String): DeviceToken?
}
