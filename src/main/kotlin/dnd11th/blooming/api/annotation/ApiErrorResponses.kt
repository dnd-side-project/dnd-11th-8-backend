package dnd11th.blooming.api.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorResponses(
    val value: Array<ApiErrorResponse>,
)
