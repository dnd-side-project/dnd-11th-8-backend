package dnd11th.blooming.common.exception

import org.springframework.http.HttpStatus

class InvalidDateException(
    message: String = "올바르지 않은 날짜입니다.",
) : MyException(statusCode, code, message) {
    companion object {
        val statusCode = HttpStatus.BAD_REQUEST
        val code = ExceptionCode.INVALID_DATE
    }
}
