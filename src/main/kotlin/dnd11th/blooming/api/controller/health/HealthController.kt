package dnd11th.blooming.api.controller.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HealthController : HealthApi {
    @GetMapping("/health")
    override fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok().body("ok")
    }
}
