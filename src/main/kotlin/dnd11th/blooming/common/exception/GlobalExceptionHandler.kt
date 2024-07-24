package dnd11th.blooming.common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MyException::class)
    fun handleMyException(exception: MyException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(exception.status)
            .body(ErrorResponse.from(exception))
    }
}
