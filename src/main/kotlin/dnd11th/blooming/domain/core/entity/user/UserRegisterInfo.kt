package dnd11th.blooming.domain.core.entity.user

data class UserRegisterInfo(
    val nickname: String,
    val alarmTime: AlarmTime,
    val regionId: Int,
)
