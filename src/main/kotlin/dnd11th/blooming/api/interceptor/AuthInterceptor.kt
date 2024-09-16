package dnd11th.blooming.api.interceptor

import dnd11th.blooming.api.annotation.Secured
import dnd11th.blooming.api.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val jwtProvider: JwtProvider,
) : HandlerInterceptor {
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
        private const val ATTRIBUTE_KEY = "claims"
        private const val OPTIONS = "OPTIONS"
    }

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        if (isPreflight(request)) return true
        if (handler !is HandlerMethod) {
            return super.preHandle(request, response, handler)
        }
        if (isSecured(handler)) processAuthenticate(request)
        return super.preHandle(request, response, handler)
    }

    private fun isPreflight(request: HttpServletRequest): Boolean {
        return request.method == OPTIONS
    }

    private fun isSecured(handler: Any): Boolean {
        return (handler as HandlerMethod).hasMethodAnnotation(Secured::class.java)
    }

    private fun processAuthenticate(request: HttpServletRequest) {
        val token = extractToken(request)
        val userClaims = jwtProvider.resolveAccessToken(token)
        request.setAttribute(ATTRIBUTE_KEY, userClaims)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(HEADER_AUTHORIZATION)
        return if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length)
        } else {
            null
        }
    }
}
