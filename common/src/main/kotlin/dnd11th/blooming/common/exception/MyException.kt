package dnd11th.blooming.common.exception

abstract class MyException(
    val errorType: ErrorType,
) : RuntimeException(errorType.message)
