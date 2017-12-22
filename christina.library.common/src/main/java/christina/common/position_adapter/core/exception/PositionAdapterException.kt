package christina.common.position_adapter.core.exception

import christina.common.exception.ExceptionDefaultSettings

open class PositionAdapterException
@JvmOverloads
constructor(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.enableSuppression,
    writableStackTrace: Boolean = ExceptionDefaultSettings.writableStackTrace
) : Exception(message, cause, enableSuppression, writableStackTrace)