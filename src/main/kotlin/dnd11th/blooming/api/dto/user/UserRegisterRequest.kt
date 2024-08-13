package dnd11th.blooming.api.dto.user

import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.UserRegisterInfo

data class UserRegisterRequest(
    val nickname: String,
    val alarmTime: Int,
    val ctpId: Int,
    val sigId: Int,
) {
    fun toUserRegisterInfo(): UserRegisterInfo  {
        return UserRegisterInfo(
            nickname = nickname,
            alarmTime = AlarmTime.from(alarmTime),
            ctpId = ctpId,
            sigId = sigId,
        )
    }
}
