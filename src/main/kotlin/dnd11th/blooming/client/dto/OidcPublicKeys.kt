package dnd11th.blooming.client.dto

data class OidcPublicKeys(
    val keys: List<OidcPublicKey>,
) {
    fun getMatchesKey(kid: String): OidcPublicKey {
        return keys.firstOrNull { it.kid == kid }
            ?: throw IllegalArgumentException("JWT 값의 kid 정보가 올바르지 않습니다.")
    }
}
