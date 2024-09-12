package dnd11th.blooming.client.fcm

interface FcmService {
    suspend fun send(pushNotification: PushNotification)

    suspend fun mock(pushNotification: PushNotification)
}
