package christina.common.exception

@JvmOverloads
inline fun <reified TEnum : Enum<TEnum>> reasonableException(
    reason: TEnum,
    reasonDescription: String? = null,
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.enableSuppression,
    writableStackTrace: Boolean = ExceptionDefaultSettings.writableStackTrace) =
    ReasonableException(
        TEnum::class.qualifiedName,
        reason.name,
        reasonDescription,
        message,
        cause,
        enableSuppression,
        writableStackTrace)

inline fun <reified TEnum : Enum<TEnum>> ReasonableException.hasReason(reason: TEnum) =
    reasonGroup == TEnum::class.qualifiedName && this.reason == reason.name