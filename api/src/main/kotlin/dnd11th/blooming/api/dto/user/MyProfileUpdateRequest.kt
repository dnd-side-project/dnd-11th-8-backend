package dnd11th.blooming.api.dto.user

import io.swagger.v3.oas.annotations.media.Schema

sealed class MyProfileUpdateRequest {
    @Schema(
        name = "Update Nickname Request",
        description = "마이페이지 닉네임 수정",
    )
    data class Nickname(
        @field:Schema(description = "닉네임", example = "스탑환")
        val nickname: String,
    )

    @Schema(
        name = "Update AlarmStatus Request",
        description = "마이페이지 알림 상태 수정",
    )
    data class AlarmStatus(
        @field:Schema(description = "알림상태", example = "true or false")
        val alarmStatus: Boolean,
    )

    @Schema(
        name = "Update AlarmTime Request",
        description = "마이페이지 알림 시간대 수정",
    )
    data class AlarmTime(
        @field:Schema(description = "알림시간대", example = "1,2...19")
        val alarmTime: Int,
    )

    data class Region(
        val regionId: Int,
    )
}
