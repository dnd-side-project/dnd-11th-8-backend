package dnd11th.blooming.client.fcm

interface FcmService {
    fun sendNotification(pushNotification: PushNotification)
}
