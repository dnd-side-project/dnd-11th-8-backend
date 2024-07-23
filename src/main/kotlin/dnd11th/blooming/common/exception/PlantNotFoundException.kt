package dnd11th.blooming.common.exception

import dnd11th.blooming.common.util.ExceptionCode
import org.springframework.http.HttpStatus

class PlantNotFoundException(
    message: String = "존재하지 않는 식물입니다.",
) : MyException(statusCode, code, message) {
    companion object {
        val statusCode = HttpStatus.NOT_FOUND
        val code = ExceptionCode.NOT_FOUND_PLANT_ID
    }
}
