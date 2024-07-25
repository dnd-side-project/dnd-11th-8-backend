package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
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
}
