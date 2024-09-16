package dnd11th.blooming.api.controller.exception

import dnd11th.blooming.common.exception.ErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.FieldErrorResponse
import dnd11th.blooming.common.exception.MyException
import dnd11th.blooming.common.util.Logger.Companion.log
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
            LogLevel.ERROR -> {
                log.error { "Blooming Exception: ${errorType.message}, Exception: $exception" }
            }
            LogLevel.WARN -> {
                log.warn { "Blooming Exception: ${errorType.message}, Exception: $exception" }
            }
            else -> {
                log.info { "Blooming Exception: ${errorType.message}, Exception: $exception" }
            }
        }
        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.BAD_REQUEST

        // bindingResult를 순회하며 errorArgumentMap을 채운다.
        val fieldErrorList: List<FieldErrorResponse> =
            exception.bindingResult.allErrors.reversed()
                .mapNotNull { error ->
                    val field = (error as? FieldError)?.field
                    val message = error?.defaultMessage
                    if (field != null && message != null) {
                        FieldErrorResponse(field, message)
                    } else {
                        null
                    }
                }

        // 가장 처음 발생한 bindingResult의 message를 예외의 message로 처리한다.
        exception.bindingResult.allErrors.last().defaultMessage?.also {
            errorType.message = it
        }

        return ResponseEntity
            .status(exception.statusCode)
            .body(ErrorResponse.from(errorType, fieldErrorList))
    }
}
