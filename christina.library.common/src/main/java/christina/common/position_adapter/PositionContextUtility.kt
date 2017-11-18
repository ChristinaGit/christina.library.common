package christina.common.position_adapter

fun positionContext(positionStart: () -> Int, positionEnd: () -> Int): PositionContext =
    object : PositionContext {
        override fun getPositionStart(): Int = positionStart()

        override fun getPositionEnd(): Int = positionEnd()
    }

fun positionContext(positionStart: Int, positionEnd: Int): PositionContext =
    object : PositionContext {
        override fun getPositionStart(): Int = positionStart

        override fun getPositionEnd(): Int = positionEnd
    }

fun positionContext(positionRange: IntRange) = positionContext(positionRange.start, positionRange.endInclusive)