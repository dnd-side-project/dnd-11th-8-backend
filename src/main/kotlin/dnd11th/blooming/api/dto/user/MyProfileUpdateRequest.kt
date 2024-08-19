package dnd11th.blooming.api.dto.user

sealed class MyProfileUpdateRequest {

    data class Nickname(
        val nickname: String
    )

    data class Region(
        val regionId: Int
    )

    data class AlarmStatus(
        val alarmStatus: Boolean
    )

    data class AlarmTime(
        val alarmTime: Int
    )
}
