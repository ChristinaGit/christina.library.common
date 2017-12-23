package christina.common.exception

open class IllegalOperationException
@JvmOverloads
constructor(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.ENABLE_SUPPRESSION,
    writableStackTrace: Boolean = ExceptionDefaultSettings.WRITABLE_STACKTRACE
) : RuntimeException(message, cause, enableSuppression, writableStackTrace)