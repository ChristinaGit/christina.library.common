package christina.common.core.accessor

inline fun <Target, Value> Accessor<Target, Value>.toMutableAccessor(
    crossinline setter: (Target, Value) -> Unit
): MutableAccessor<Target, Value> =
    object : MutableAccessor<Target, Value> {
        override fun get(target: Target): Value = this@toMutableAccessor.get(target)

        override fun set(target: Target, value: Value) = setter(target, value)
    }