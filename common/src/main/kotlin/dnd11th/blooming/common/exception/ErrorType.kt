package dnd11th.blooming.common.exception

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, var message: String, val logLevel: LogLevel) {
    // COMMON
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred.", LogLevel.ERROR),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 파라미터가 올바르지 않습니다.", LogLevel.DEBUG),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다.", LogLevel.DEBUG),
    HTTP_METHOD_NOT_SUPPORTED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP METHOD 입니다.", LogLevel.DEBUG),
    PATH_VARIABLE_MISSING(HttpStatus.BAD_REQUEST, "경로 변수가 누락되었습니다.", LogLevel.DEBUG),
    REQUEST_PARAMETER_MISSING(HttpStatus.BAD_REQUEST, "요청 파라미터가 누락되었습니다.", LogLevel.DEBUG),
    ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "경로 변수 혹은 요청 파라미터 타입이 올바르지 않습니다.", LogLevel.DEBUG),

    // MyPlant
    NOT_FOUND_MYPLANT(HttpStatus.NOT_FOUND, "존재하지 않는 내 식물입니다.", LogLevel.DEBUG),

    // Plant
    NOT_FOUND_PLANT(HttpStatus.NOT_FOUND, "존재하지 않는 식물입니다.", LogLevel.DEBUG),

    // Image
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다.", LogLevel.DEBUG),

    // Location
    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "존재하지 않는 위치입니다.", LogLevel.DEBUG),
    LOCATION_NAME_DUPLICATE(HttpStatus.NOT_FOUND, "이미 존재하는 위치명입니다.", LogLevel.DEBUG),
    LOCATION_COUNT_EXCEED(HttpStatus.BAD_REQUEST, "위치는 최대 3개까지만 등록 가능합니다.", LogLevel.DEBUG),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.", LogLevel.DEBUG),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.", LogLevel.DEBUG),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다", LogLevel.DEBUG),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 ID TOKEN입니다.", LogLevel.WARN),
    INVALID_MATCHING_KEY(HttpStatus.BAD_GATEWAY, "응답값과 매칭되는 키가 존재하지 않습니다.", LogLevel.WARN),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 RefreshToken입니다.", LogLevel.DEBUG),
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 provider입니다", LogLevel.DEBUG),
    DUPLICATE_USER_LOGIN(HttpStatus.UNAUTHORIZED, "중복된 로그인이 감지되었습니다.", LogLevel.DEBUG),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다.", LogLevel.DEBUG),

    // CLIENT
    OPEN_API_CALL_EXCEPTION(HttpStatus.BAD_GATEWAY, "OpenAPI 호출에 실패했습니다", LogLevel.WARN),
    FIREBASE_API_CALL_EXCEPTION(HttpStatus.BAD_GATEWAY, "FCM 호출에 실패했습니다", LogLevel.WARN),

    // REGION
    NOT_FOUND_REGION(HttpStatus.NOT_FOUND, "존재하지 않는 지역번호입니다.", LogLevel.DEBUG),

    // Redis
    NOT_FOUND_REDIS_KEY(HttpStatus.BAD_REQUEST, "해당 Key가 존재하지 않습니다.", LogLevel.DEBUG),
}
