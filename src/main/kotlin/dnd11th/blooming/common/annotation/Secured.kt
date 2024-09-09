package dnd11th.blooming.common.annotation

import io.swagger.v3.oas.annotations.security.SecurityRequirement

/**
 * AccessToken이 필요한 API에 적용하는 어노테이션입니다.
 */
@SecurityRequirement(name = "AccessToken")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Secured
