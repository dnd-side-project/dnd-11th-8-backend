package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val message: String, val logLevel: LogLevel) {
    INVALID_DATE(HttpStatus.BAD_REQUEST, "올바르지 않은 날짜입니다.", LogLevel.DEBUG),
    NOT_FOUND_MYPLANT_ID(HttpStatus.NOT_FOUND, "존재하지 않는 내 식물입니다.", LogLevel.DEBUG),
}
