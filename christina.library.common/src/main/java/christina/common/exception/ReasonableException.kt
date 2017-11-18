package christina.common.exception

open class ReasonableException(
    val reasonGroup: String? = null,
    val reason: String? = null,
    val reasonDescription: String? = null,
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = true
) : Exception(message, cause, enableSuppression, writableStackTrace)