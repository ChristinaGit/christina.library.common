package christina.common.core.accessor

interface Accessor<in Target, out Value> {
    fun get(target: Target): Value
}