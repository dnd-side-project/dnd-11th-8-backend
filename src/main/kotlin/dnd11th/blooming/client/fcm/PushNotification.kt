package dnd11th.blooming.client.fcm

data class PushNotification(
    val myPlantId: Long,
    val token: String,
    val title: String,
    val content: String,
)
