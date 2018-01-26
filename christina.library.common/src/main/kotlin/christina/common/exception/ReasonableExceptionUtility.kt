package christina.common.exception

@JvmOverloads
inline fun <reified Reason : Enum<Reason>> reasonableException(
    reason: Reason,
    reasonDescription: String? = null,
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.ENABLE_SUPPRESSION,
    writableStackTrace: Boolean = ExceptionDefaultSettings.WRITABLE_STACKTRACE
) = ReasonableException(
    Reason::class.qualifiedName,
    reason.name,
    reasonDescription,
    message,
    cause,
    enableSuppression,
    writableStackTrace)

inline fun <reified Reason : Enum<Reason>> ReasonableException.hasReason(reason: Reason) =
    reasonGroup == Reason::class.qualifiedName && this.reason == reason.name