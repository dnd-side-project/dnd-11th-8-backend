package dnd11th.blooming.client.oauth

import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.ExternalServerException

data class OidcPublicKeys(
    val keys: List<OidcPublicKey>,
) {
    fun getMatchesKey(
        kid: String,
        alg: String,
    ): OidcPublicKey {
        return keys.firstOrNull { it.kid == kid && it.alg == alg }
            ?: throw ExternalServerException(ErrorType.INVALID_MATCHING_KEY)
    }
}
