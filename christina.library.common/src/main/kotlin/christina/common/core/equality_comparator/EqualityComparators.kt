package christina.common.core.equality_comparator

object EqualityComparators {
    @JvmStatic
    private val default: EqualityComparator<Any?> =
        object : EqualityComparator<Any?> {
            override fun equals(lhs: Any?, rhs: Any?): Boolean = lhs == rhs
        }

    @JvmStatic
    private val reference: EqualityComparator<Any?> =
        object : EqualityComparator<Any?> {
            override fun equals(lhs: Any?, rhs: Any?): Boolean = lhs === rhs
        }

    @JvmStatic
    fun default() = default

    @JvmStatic
    fun reference() = reference
}