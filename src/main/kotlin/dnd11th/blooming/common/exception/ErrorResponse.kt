package dnd11th.blooming.common.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse private constructor(
    val code: ErrorType,
    val message: String,
    val field: List<String>?,
) {
    companion object {
        fun from(errorType: ErrorType, field: List<String>? = null): ErrorResponse =
            ErrorResponse(
                code = errorType,
                message = errorType.message,
                field = field
            )
    }
}
