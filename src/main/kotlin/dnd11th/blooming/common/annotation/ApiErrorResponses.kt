package dnd11th.blooming.common.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponses(
    val value: Array<ApiErrorResponse>,
)
