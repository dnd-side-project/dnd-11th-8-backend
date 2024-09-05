package dnd11th.blooming.common.holder

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SystemInstantHolder : InstantHolder {
    override fun now(): Instant {
        return Instant.now()
    }
}
