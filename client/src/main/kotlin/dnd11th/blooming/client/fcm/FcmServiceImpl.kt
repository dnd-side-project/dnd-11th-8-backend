package dnd11th.blooming.client.fcm

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.ExternalServerException
import org.springframework.stereotype.Service

@Service
class FcmServiceImpl : FcmService {
    override fun send(pushNotification: PushNotification) {
        try {
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
            if (FirebaseApp.getApps().isNotEmpty()) {
                FirebaseMessaging.getInstance().sendAsync(message)
            }
        } catch (e: FirebaseMessagingException) {
            throw ExternalServerException(ErrorType.FIREBASE_API_CALL_EXCEPTION)
        }
    }

    override fun mock(pushNotification: PushNotification) {
        try {
            Thread.sleep(50)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
