package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.domain.core.entity.user.OauthProvider
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class OpenIdTokenResolverSelector(
    private val applicationContext: ApplicationContext,
) {
    fun select(oauthProvider: OauthProvider): OpenIdTokenResolver {
        return when (oauthProvider) {
            OauthProvider.KAKAO -> applicationContext.getBean(KakaoIdTokenResolver::class.java)
            OauthProvider.APPLE -> applicationContext.getBean(AppleIdTokenResolver::class.java)
        }
    }
}
