package dnd11th.blooming.api.controller

import dnd11th.blooming.common.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController {
    @Secured
    @GetMapping("/authorize")
    fun authorize() {}

    @GetMapping("/unauthorize")
    fun unAuthorize() {}
}
