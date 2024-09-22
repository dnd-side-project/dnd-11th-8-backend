package dnd11th.blooming

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan
class Dnd11th8BackendBatchApplication

fun main(args: Array<String>) {
    runApplication<Dnd11th8BackendBatchApplication>(*args)
}
