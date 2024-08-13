package dnd11th.blooming.domain.entity.user

data class UserRegisterInfo(
    val nickname: String,
    val alarmTime: AlarmTime,
    val ctpId: Int,
    val sigId: Int,
)
