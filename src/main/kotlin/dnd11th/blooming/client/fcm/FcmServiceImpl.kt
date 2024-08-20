package dnd11th.blooming.client.fcm

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service

@Service
class FcmServiceImpl : FcmService {
    override fun sendNotification(pushNotification: PushNotification) {
        val notification: Notification =
            Notification.builder()
                .setTitle(pushNotification.title)
                .setBody(pushNotification.content)
                .build()

        val message: Message =
            Message
                .builder()
                .setToken(pushNotification.token)
                .setNotification(notification)
                .putData("myPlantId", pushNotification.myPlantId.toString())
                .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseMessaging.getInstance().send(message)
        }
    }
}
