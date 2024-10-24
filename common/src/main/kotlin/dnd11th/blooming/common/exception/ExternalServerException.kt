package dnd11th.blooming.common.exception

class ExternalServerException(
    errorType: ErrorType,
    private val customMessage: String? = null,
) : MyException(errorType) {
    override val message: String
        get() = customMessage ?: errorType.message
}
