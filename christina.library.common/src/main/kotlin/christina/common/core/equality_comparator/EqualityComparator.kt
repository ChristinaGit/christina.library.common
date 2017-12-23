package christina.common.core.equality_comparator

@FunctionalInterface
interface EqualityComparator<in T> {
    fun equals(lhs: T, rhs: T): Boolean
}