package dnd11th.blooming.api.controller.user

import dnd11th.blooming.api.dto.user.MyProfileResponse
import dnd11th.blooming.api.dto.user.MyProfileUpdateRequest
import dnd11th.blooming.api.dto.user.TokenResponse
import dnd11th.blooming.api.dto.user.UserRegisterRequest
import dnd11th.blooming.api.service.user.UserProfileService
import dnd11th.blooming.api.service.user.UserRegisterService
import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.common.annotation.PendingUser
import dnd11th.blooming.common.annotation.Secured
import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.OauthProvider
import dnd11th.blooming.domain.entity.user.RegisterClaims
import dnd11th.blooming.domain.entity.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userRegisterService: UserRegisterService,
    private val userProfileService: UserProfileService,
) : UserApi {
    @PostMapping("/register")
    override fun register(
        @PendingUser registerClaims: RegisterClaims,
        @RequestBody userRegisterRequest: UserRegisterRequest,
    ): TokenResponse {
        return userRegisterService.registerUser(
            registerClaims.email,
            OauthProvider.from(registerClaims.provider),
            userRegisterRequest.toUserRegisterInfo(),
        )
    }

    @Secured
    @GetMapping("/my")
    override fun findProfile(
        @LoginUser user: User,
    ): MyProfileResponse {
        return userProfileService.findProfile(user)
    }

    @Secured
    @PatchMapping("/my/nickname")
    override fun updateNickname(
        @LoginUser user: User,
        @RequestBody updateNickName: MyProfileUpdateRequest.Nickname,
    ) {
        userProfileService.updateNickname(user, updateNickName.nickname)
    }

    @Secured
    @PatchMapping("/my/alarm")
    override fun updateAlarmStatus(
        @LoginUser user: User,
        @RequestBody updateAlarmStatus: MyProfileUpdateRequest.AlarmStatus,
    ) {
        userProfileService.updateAlarmStatus(user, updateAlarmStatus.alarmStatus)
    }

    @Secured
    @PatchMapping("/my/alarm-time")
    override fun updateAlarmTime(
        @LoginUser user: User,
        @RequestBody updateAlarmTime: MyProfileUpdateRequest.AlarmTime,
    ) {
        userProfileService.updateAlarmTime(user, AlarmTime.from(updateAlarmTime.alarmTime))
    }
}
