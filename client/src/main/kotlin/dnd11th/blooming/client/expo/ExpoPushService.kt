package dnd11th.blooming.client.expo

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.ExternalServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class ExpoPushService(
    private val expoPushClient: ExpoPushClient,
) : PushService {
    override suspend fun send(push: List<PushNotification>) {
        val response: PushNotificationResponse =
            withContext(Dispatchers.IO) {
                expoPushClient.sendPushNotification(push)
            }

        val errorMessages: List<String> =
            response.data
                .filter { it.status == "error" }
                .mapNotNull { it.message }

        if (errorMessages.isNotEmpty()) {
            throw ExternalServerException(ErrorType.PUSH_API_CALL_EXCEPTION, errorMessages.joinToString(", "))
        }
    }

    override suspend fun mock(push: List<PushNotification>) {
        try {
            delay(2000L)
        } catch (e: Exception) {
            println("지연 처리 중 예외 발생: ${e.message}")
        }
    }
}
