package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MyException::class)
    fun handleMyException(exception: MyException): ResponseEntity<ErrorResponse> {
        val errorType = exception.errorType
        when (errorType.logLevel) {
            LogLevel.ERROR -> {}
            LogLevel.WARN -> {}
            else -> {}
        }
        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.ARGUMENT_ERROR

        // bindingResult를 순회하며 errorArgumentMap을 채운다.
        val fieldErrorList: List<FieldErrorResponse> =
            exception.bindingResult.allErrors
                .mapNotNull { error ->
                    val field = (error as? FieldError)?.field
                    val message = error?.defaultMessage
                    if (field != null && message != null) {
                        FieldErrorResponse(field, message)
                    } else {
                        null
                    }
                }

        // 가장 첫번째 bindingResult의 message를 예외의 message로 처리한다.
        exception.bindingResult.allErrors[0].defaultMessage?.also {
            errorType.message = it
        }

        return ResponseEntity
            .status(exception.statusCode)
            .body(ErrorResponse.from(errorType, fieldErrorList))
    }
}
