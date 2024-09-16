package dnd11th.blooming.api.dto.user

import dnd11th.blooming.domain.core.entity.user.User
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "MyProfile Response",
    description = "마이페이지 응답",
)
data class MyProfileResponse(
    @field:Schema(description = "닉네임", example = "스탑환")
    val nickname: String,
    @field:Schema(description = "내 식물 개수", example = "2")
    val myPlantCount: Int,
    @field:Schema(description = "내 알림 개수", example = "2")
    val alarmCount: Int,
    @field:Schema(description = "알림 여부", example = "true")
    val alarmStatus: Boolean,
    @field:Schema(description = "알림 시간대", example = "1")
    val alarmTime: Int,
) {
    companion object {
        fun of(
            user: User,
            myPlantCount: Int,
        ): MyProfileResponse {
            return MyProfileResponse(
                nickname = user.nickname,
                myPlantCount = myPlantCount,
                alarmCount = 1,
                alarmStatus = user.alarmStatus,
                alarmTime = user.alarmTime.code,
            )
        }
    }
}
