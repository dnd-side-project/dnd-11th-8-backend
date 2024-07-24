package dnd11th.blooming.common.exception

import org.springframework.http.HttpStatus

enum class ErrorType(val status: HttpStatus, val message: String) {
    INVALID_DATE(HttpStatus.BAD_REQUEST, "올바르지 않은 날짜입니다."),
    NOT_FOUND_PLANT_ID(HttpStatus.NOT_FOUND, "존재하지 않는 식물입니다."),
}
