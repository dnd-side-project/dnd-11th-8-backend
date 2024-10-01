package dnd11th.blooming.client.fcm

interface FcmService {
    fun send(pushNotification: PushNotification)

    fun mock(pushNotification: PushNotification)
}
