package christina.common.exception

open class ReasonableException
@JvmOverloads
constructor(
    val reasonGroup: String? = null,
    val reason: String? = null,
    val reasonDescription: String? = null,
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = ExceptionDefaultSettings.enableSuppression,
    writableStackTrace: Boolean = ExceptionDefaultSettings.writableStackTrace
) : RuntimeException(buildMessage(reasonGroup, reason, reasonDescription, message), cause, enableSuppression, writableStackTrace) {
    companion object {
        @JvmStatic
        private fun buildMessage(reasonGroup: String?,
                                 reason: String?,
                                 reasonDescription: String?,
                                 message: String?): String? =
            if (reasonGroup === null
                && reason === null
                && reasonDescription === null) {
                message
            } else {
                buildString {
                    if (message !== null) {
                        append(message)
                    }
                    if (reason !== null) {
                        append("By reason: ")
                        append(reason)
                        append(". ")
                    }
                    if (reasonGroup !== null) {
                        append("From group: ")
                        append(reasonGroup)
                        append(". ")
                    }
                    if (reasonDescription !== null) {
                        append(reasonDescription)
                    }
                }
            }
    }
}