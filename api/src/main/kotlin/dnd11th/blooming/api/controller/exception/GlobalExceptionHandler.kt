package dnd11th.blooming.api.controller.exception

import dnd11th.blooming.common.exception.ErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import dnd11th.blooming.common.exception.FieldErrorResponse
import dnd11th.blooming.common.exception.MyException
import dnd11th.blooming.common.util.Logger.Companion.log
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MyException::class)
    fun handleMyException(exception: MyException): ResponseEntity<ErrorResponse> {
        val errorType = exception.errorType

        logException(errorType, exception, ExceptionSource.BLOOMING)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.BAD_REQUEST

        logException(errorType, exception, ExceptionSource.HTTP)

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

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.HTTP_MESSAGE_NOT_READABLE

        logException(errorType, exception, ExceptionSource.HTTP)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.HTTP_METHOD_NOT_SUPPORTED

        logException(errorType, exception, ExceptionSource.HTTP)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MissingPathVariableException::class)
    fun handleMissingPathVariableException(exception: MissingPathVariableException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.PATH_VARIABLE_MISSING

        logException(errorType, exception, ExceptionSource.HTTP)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.REQUEST_PARAMETER_MISSING

        logException(errorType, exception, ExceptionSource.HTTP)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val errorType = ErrorType.ARUGMENT_TYPE_MISMATCH

        logException(errorType, exception, ExceptionSource.HTTP)

        return ResponseEntity
            .status(errorType.status)
            .body(ErrorResponse.from(errorType))
    }

    private fun logException(
        errorType: ErrorType,
        exception: Exception,
        exceptionSource: ExceptionSource,
    ) {
        when (errorType.logLevel) {
            LogLevel.ERROR -> log.error { "$exceptionSource Exception: ${errorType.message}, Exception: $exception" }
            LogLevel.WARN -> log.warn { "$exceptionSource Exception: ${errorType.message}, Exception: $exception" }
            else -> log.info { "$exceptionSource Exception: ${errorType.message}, Exception: $exception" }
        }
    }

    private enum class ExceptionSource {
        BLOOMING,
        HTTP,
    }
}
