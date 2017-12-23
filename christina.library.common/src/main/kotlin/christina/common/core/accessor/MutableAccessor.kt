package christina.common.core.accessor

interface MutableAccessor<in Target, Value>: Accessor<Target, Value> {
    fun set(target: Target, value: Value)
}