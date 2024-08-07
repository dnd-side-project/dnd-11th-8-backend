package dnd11th.blooming.common.annotation

import dnd11th.blooming.common.exception.ErrorType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponse(
    val errorType: ErrorType,
    val description: String,
)
