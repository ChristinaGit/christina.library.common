package christina.common.core.equality_comparator

fun <T> Comparator<T>.asEqualityComparator(): EqualityComparator<T> =
    equalityComparator { lhs, rhs -> compare(lhs, rhs) == 0 }