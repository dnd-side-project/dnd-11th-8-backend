package dnd11th.blooming.api.resolver

import dnd11th.blooming.api.annotation.PendingUser
import dnd11th.blooming.api.jwt.JwtProvider
import dnd11th.blooming.domain.core.entity.user.RegisterClaims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class PendingUserArgumentResolver(
    private val jwtProvider: JwtProvider,
) : HandlerMethodArgumentResolver {
    companion object {
        private const val ATTRIBUTE_KEY = "RegisterToken"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(PendingUser::class.java) &&
            parameter.parameterType === RegisterClaims::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest
        val registerToken: String? = request.getHeader(ATTRIBUTE_KEY) ?: null
        return jwtProvider.resolveRegisterToken(registerToken)
    }
}
