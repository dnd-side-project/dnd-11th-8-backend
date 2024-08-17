package dnd11th.blooming.api.dto.user

import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.UserRegisterInfo
import io.swagger.v3.oas.annotations.media.Schema

data class UserRegisterRequest(
    @field:Schema(description = "닉네임", example = "스탑환")
    val nickname: String,
    @field:Schema(description = "알람 시간대", example = "1~19 중 숫자 하나")
    val alarmTime: Int,
    @field:Schema(description = "1단계 행정구역", example = "1")
    val ctpId: Int,
    @field:Schema(description = "2단계 행정구역", example = "2")
    val sigId: Int,
) {
    fun toUserRegisterInfo(): UserRegisterInfo {
        return UserRegisterInfo(
            nickname = nickname,
            alarmTime = AlarmTime.from(alarmTime),
            ctpId = ctpId,
            sigId = sigId,
        )
    }
}
