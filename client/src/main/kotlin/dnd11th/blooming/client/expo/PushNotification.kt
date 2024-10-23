package dnd11th.blooming.client.expo

data class PushNotification(
    val to: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun create(
            token: String,
            plantNickname: String,
        ): PushNotification {
            return PushNotification(
                to = token,
                title = "블루밍",
                content = "${plantNickname}에 물이 필요해요",
            )
        }
    }
}
