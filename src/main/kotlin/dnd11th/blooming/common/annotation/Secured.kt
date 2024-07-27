package dnd11th.blooming.common.annotation

/**
 * AccessToken이 필요한 API에 적용하는 어노테이션입니다.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Secured
