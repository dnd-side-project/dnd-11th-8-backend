package dnd11th.blooming.client.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FcmConfig(
    @Value("\${fcm.key}")
    private val fcmPrivateKeyPath: String,
) {
    @PostConstruct
    fun initialize() {
        val googleCredentials: GoogleCredentials =
            GoogleCredentials.fromStream(ClassPathResource(fcmPrivateKeyPath).inputStream)
        val fireBaseOptions: FirebaseOptions =
            FirebaseOptions.builder().setCredentials(googleCredentials).build()
        FirebaseApp.initializeApp(fireBaseOptions)
    }
}
