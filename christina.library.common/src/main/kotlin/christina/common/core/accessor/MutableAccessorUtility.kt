package christina.common.core.accessor

inline fun <Target, Value> mutableAccessor(
    crossinline getter: (Target) -> Value,
    crossinline setter: (Target, Value) -> Unit
): MutableAccessor<Target, Value> =
    object : MutableAccessor<Target, Value> {
        override fun get(target: Target): Value = getter(target)
        override fun set(target: Target, value: Value) = setter(target, value)
    }