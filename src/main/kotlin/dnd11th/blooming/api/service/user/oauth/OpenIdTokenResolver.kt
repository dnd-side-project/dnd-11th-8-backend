package dnd11th.blooming.api.service.user.oauth

import dnd11th.blooming.domain.core.entity.user.OidcUser

interface OpenIdTokenResolver {
    fun resolveIdToken(idToken: String): OidcUser
}
