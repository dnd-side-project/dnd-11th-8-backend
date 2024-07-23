package dnd11th.blooming.common.exception

import dnd11th.blooming.common.util.ExceptionCode
import org.springframework.http.HttpStatus

abstract class MyException(
    val status: HttpStatus,
    val code: ExceptionCode,
    message: String,
    val field: List<String>? = null,
) : RuntimeException(message)
