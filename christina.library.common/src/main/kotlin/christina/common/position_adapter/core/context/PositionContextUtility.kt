package christina.common.position_adapter.core.context

import android.support.annotation.CheckResult

@CheckResult
inline fun positionContext(crossinline positionStart: () -> Int, crossinline positionEnd: () -> Int): PositionContext =
    object : PositionContext {
        override fun getPositionStart(): Int = positionStart()

        override fun getPositionEnd(): Int = positionEnd()
    }

@CheckResult
fun positionContext(positionStart: Int, positionEnd: Int): PositionContext =
    object : PositionContext {
        override fun getPositionStart(): Int = positionStart

        override fun getPositionEnd(): Int = positionEnd
    }

@CheckResult
fun positionContext(positionRange: IntRange) = positionContext(positionRange.start, positionRange.endInclusive)