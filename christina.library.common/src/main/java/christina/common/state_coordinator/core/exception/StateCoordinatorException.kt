package christina.common.state_coordinator.core.exception

import christina.common.exception.ExceptionDefaultSettings

open class StateCoordinatorException
@JvmOverloads
constructor(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.ENABLE_SUPPRESSION,
    writableStackTrace: Boolean = ExceptionDefaultSettings.WRITABLE_STACKTRACE
) : Exception(message, cause, enableSuppression, writableStackTrace)