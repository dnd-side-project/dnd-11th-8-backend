package dnd11th.blooming.client.expo

data class PushNotificationResponse(
    val data: List<PushResponse>,
)

data class PushResponse(
    val status: String,
    val id: String?,
    val message: String?,
    val details: ErrorDetails?,
)

data class ErrorDetails(
    val error: String?,
)
