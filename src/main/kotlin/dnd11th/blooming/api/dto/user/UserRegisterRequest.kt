package dnd11th.blooming.api.dto.user

import dnd11th.blooming.domain.entity.user.AlarmTime
import dnd11th.blooming.domain.entity.user.UserRegisterInfo
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "User Register Request",
    description = "사용자 등록 요청",
)
data class UserRegisterRequest(
    @field:Schema(description = "닉네임", example = "스탑환")
    val nickname: String,
    @field:Schema(description = "알람 시간대", example = "1~19 중 숫자 하나")
    val alarmTime: Int,
    @field:Schema(description = "지역 PK", example = "1")
    val regionId: Int,
) {
    fun toUserRegisterInfo(): UserRegisterInfo {
        return UserRegisterInfo(
            nickname = nickname,
            alarmTime = AlarmTime.from(alarmTime),
            regionId = regionId,
        )
    }
}
