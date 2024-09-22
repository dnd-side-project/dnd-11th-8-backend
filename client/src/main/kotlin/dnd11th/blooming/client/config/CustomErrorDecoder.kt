package dnd11th.blooming.client.config

import dnd11th.blooming.common.util.Logger.Companion.log
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception

class CustomErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        val status = response?.status()
        val body = response?.body()?.asInputStream()?.bufferedReader().use { it?.readText() }
        log.error { "Feign error in method: $methodKey, status: $status, body: $body" }
        return FeignException.errorStatus(methodKey, response)
    }
}
