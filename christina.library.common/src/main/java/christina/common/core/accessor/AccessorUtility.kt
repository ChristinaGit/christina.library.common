package christina.common.core.accessor

inline fun <Target, Value> accessor(crossinline getter: (Target) -> Value): Accessor<Target, Value> =
    object : Accessor<Target, Value> {
        override fun get(target: Target): Value = getter(target)
    }