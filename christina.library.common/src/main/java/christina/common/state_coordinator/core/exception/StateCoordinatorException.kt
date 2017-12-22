package christina.common.state_coordinator.core.exception

import christina.common.exception.ExceptionDefaultSettings

open class StateCoordinatorException
@JvmOverloads
constructor(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.enableSuppression,
    writableStackTrace: Boolean = ExceptionDefaultSettings.writableStackTrace
) : Exception(message, cause, enableSuppression, writableStackTrace)