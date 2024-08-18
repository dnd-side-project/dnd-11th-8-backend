package dnd11th.blooming.api.dto.user

import dnd11th.blooming.domain.entity.user.User

data class MyProfileResponse(
    val nickname: String,
    val myPlantCount: Int,
    val alarmCount: Int,
    val alarmStatus: Boolean,
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
