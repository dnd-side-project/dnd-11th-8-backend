package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, var message: String, val logLevel: LogLevel) {
    // COMMON
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred.", LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 파라미터가 올바르지 않습니다.", LogLevel.DEBUG),
    CREATE_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "추가할 수 없습니다.", LogLevel.DEBUG),
    MODIFY_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "수정할 수 없습니다.", LogLevel.DEBUG),
    DELETE_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "삭제할 수 없습니다.", LogLevel.DEBUG),

    // MyPlant
    NOT_FOUND_MYPLANT(HttpStatus.NOT_FOUND, "존재하지 않는 내 식물입니다.", LogLevel.DEBUG),

    // Plant
    NOT_FOUND_PLANT(HttpStatus.NOT_FOUND, "존재하지 않는 식물입니다.", LogLevel.DEBUG),

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
