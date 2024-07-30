package dnd11th.blooming.api.controller.user

import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController {
    @Secured
    @GetMapping("/authorize")
    fun authorize(
        @LoginUser user: User,
    ) {}

    @GetMapping("/unauthorize")
    fun unAuthorize() {}
}
