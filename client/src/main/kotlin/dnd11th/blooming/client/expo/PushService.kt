package dnd11th.blooming.client.expo

interface PushService {
    suspend fun send(push: List<PushNotification>)

    suspend fun mock(push: List<PushNotification>)
}
