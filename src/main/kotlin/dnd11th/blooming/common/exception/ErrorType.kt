package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val message: String, val logLevel: LogLevel) {
    // COMMON
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred.", LogLevel.ERROR),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "올바르지 않은 날짜입니다.", LogLevel.DEBUG),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 파라미터가 올바르지 않습니다.", LogLevel.DEBUG),

    // MyPlant
    NOT_FOUND_MYPLANT(HttpStatus.NOT_FOUND, "존재하지 않는 내 식물입니다.", LogLevel.DEBUG),

    // Image
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다.", LogLevel.DEBUG),

    // Location
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "존재하지 않는 위치입니다.", LogLevel.DEBUG),

    // Auth
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다", LogLevel.DEBUG),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 ID TOKEN입니다.", LogLevel.DEBUG),

    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 provider입니다", LogLevel.DEBUG),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다.", LogLevel.DEBUG),

    // OpenAPI
    OPEN_API_CALL_EXCEPTION(HttpStatus.BAD_REQUEST, "OpenAPI 호출에 실패했습니다", LogLevel.WARN),

    // REGION
    NOT_FOUND_REGION(HttpStatus.NOT_FOUND, "존재하지 않는 지역번호입니다.", LogLevel.DEBUG),
}
