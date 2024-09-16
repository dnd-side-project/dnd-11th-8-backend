package dnd11th.blooming.api.resolver

import dnd11th.blooming.api.annotation.LoginUser
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.NotFoundException
import dnd11th.blooming.common.exception.UnAuthorizedException
import dnd11th.blooming.domain.entity.user.User
import dnd11th.blooming.domain.entity.user.UserClaims
import dnd11th.blooming.domain.repository.user.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserArgumentResolver(
    private val userRepository: UserRepository,
) : HandlerMethodArgumentResolver {
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
                ?: throw UnAuthorizedException(ErrorType.UNAUTHORIZED)
        val userClaims: UserClaims = value as UserClaims
        return userRepository.findById(userClaims.id as Long)
            .orElseThrow { NotFoundException(ErrorType.USER_NOT_FOUND) }
    }
}
