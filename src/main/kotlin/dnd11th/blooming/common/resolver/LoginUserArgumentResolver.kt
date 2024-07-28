package dnd11th.blooming.common.resolver

import dnd11th.blooming.common.annotation.LoginUser
import dnd11th.blooming.domain.entity.User
import dnd11th.blooming.domain.entity.UserClaims
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserArgumentResolver : HandlerMethodArgumentResolver {
    companion object {
        private const val ATTRIBUTE_KEY = "claims"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java) &&
            parameter.parameterType === User::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val value =
            webRequest.getAttribute(ATTRIBUTE_KEY, RequestAttributes.SCOPE_REQUEST)
                ?: throw IllegalArgumentException()
        val userClaims = value as UserClaims
        return User.create(userClaims)
    }
}
