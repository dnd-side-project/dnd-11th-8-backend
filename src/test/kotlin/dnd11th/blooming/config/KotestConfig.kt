package dnd11th.blooming.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

class KotestConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
