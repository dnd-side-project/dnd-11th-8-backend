package dnd11th.blooming.common.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse private constructor(
    val code: ErrorType,
    val message: String,
    val fields: List<FieldErrorResponse>?,
) {
    companion object {
        fun from(
            errorType: ErrorType,
            fields: List<FieldErrorResponse>? = null,
        ): ErrorResponse =
            ErrorResponse(
                code = errorType,
                message = errorType.message,
                fields = fields,
            )
    }
}
