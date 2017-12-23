package christina.common.core.equality_comparator

inline fun <T> equalityComparator(crossinline lambda: (T, T) -> Boolean): EqualityComparator<T> =
    object : EqualityComparator<T> {
        override fun equals(lhs: T, rhs: T): Boolean = lambda(lhs, rhs)
    }