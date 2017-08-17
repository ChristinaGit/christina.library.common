package moe.christina.common.core.latebox

fun <T> (() -> T).asLateDelegate(): LateValue<T> = LateValue(this)

fun <T> T.late(): LateValue<T> = { this }.asLateDelegate()

open class LateValue<out T>(private val delegate: () -> T) {
    fun actual(): T = delegate()

    operator fun invoke(): T = actual()
}