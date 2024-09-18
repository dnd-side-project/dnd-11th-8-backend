package dnd11th.blooming.client.fcm

data class PushNotification(
    val myPlantId: Long,
    val token: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun create(
            myPlantId: Long,
            token: String,
            plantNickname: String,
        ): PushNotification {
            return PushNotification(
                myPlantId = myPlantId,
                token = token,
                title = "블루밍",
                content = "${plantNickname}에 물이 필요해요",
            )
        }
    }
}
