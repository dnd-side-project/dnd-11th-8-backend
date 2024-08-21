package dnd11th.blooming.client.oauth

data class OidcPublicKeys(
    val keys: List<OidcPublicKey>,
) {
    fun getMatchesKey(
        kid: String,
        alg: String,
    ): OidcPublicKey {
        return keys.firstOrNull { it.kid == kid && it.alg == alg }
            ?: throw IllegalArgumentException("JWT 값의 kid 또는 alg 정보가 올바르지 않습니다.")
    }
}
