package dnd11th.blooming.client.oauth

data class OidcPublicKey(
    val kty: String,
    val kid: String,
    val alg: String,
    val n: String,
    val e: String,
)
