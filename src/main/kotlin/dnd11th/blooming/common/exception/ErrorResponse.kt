package dnd11th.blooming.common.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse private constructor(
    val code: ErrorType,
    val message: String,
    val fields: Map<String, String>?,
) {
    companion object {
        fun from(
            errorType: ErrorType,
            fields: Map<String, String>? = null,
        ): ErrorResponse =
            ErrorResponse(
                code = errorType,
                message = errorType.message,
                fields = fields,
            )
    }
}
