package dnd11th.blooming.common.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val code: ExceptionCode,
    val message: String,
    val field: List<String>? = null,
) {
    companion object {
        fun from(exception: MyException): ErrorResponse =
            ErrorResponse(
                code = exception.code,
                message = exception.message ?: "",
                field = exception.field?.takeIf { it.isNotEmpty() },
            )
    }
}
