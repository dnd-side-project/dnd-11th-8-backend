package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val message: String, val logLevel: LogLevel) {
    INVALID_DATE(HttpStatus.BAD_REQUEST, "올바르지 않은 날짜입니다.", LogLevel.DEBUG),
    NOT_FOUND_MYPLANT_ID(HttpStatus.NOT_FOUND, "존재하지 않는 내 식물입니다.", LogLevel.DEBUG),
    NOT_FOUND_LOCATION_ID(HttpStatus.NOT_FOUND, "존재하지 않는 위치입니다.", LogLevel.DEBUG),

    // User
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다", LogLevel.DEBUG),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 ID TOKEN입니다.", LogLevel.DEBUG),
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 provider입니다", LogLevel.DEBUG),
}
