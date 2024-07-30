package dnd11th.blooming.api.service.user

import dnd11th.blooming.common.jwt.JwtProvider
import org.springframework.stereotype.Service

@Service
class UserTokenService(
    val jwtProvider: JwtProvider
) {

}
