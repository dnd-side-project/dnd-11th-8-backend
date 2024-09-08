package dnd11th.blooming.client.fcm

import dnd11th.blooming.batch.UserPlantDto

data class PushNotification(
    val myPlantId: Long,
    val token: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun create(userPlantDto: UserPlantDto): PushNotification {
            return PushNotification(
                myPlantId = userPlantDto.myPlantId,
                token = "deviceToken",
                title = "블루밍",
                content = "${userPlantDto.plantNickname}에 물이 필요해요",
            )
        }
    }
}
