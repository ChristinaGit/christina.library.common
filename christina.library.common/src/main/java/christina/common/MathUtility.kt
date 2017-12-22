package christina.common

infix fun Byte.divisibleBy(value: Byte) = rem(value) == 0

infix fun Short.divisibleBy(value: Short) = rem(value) == 0

infix fun Int.divisibleBy(value: Int) = rem(value) == 0

infix fun Long.divisibleBy(value: Long) = rem(value) == 0L

infix fun Byte.divides(value: Byte) = value % this == 0

infix fun Short.divides(value: Short) = value % this == 0

infix fun Int.divides(value: Int) = value % this == 0

infix fun Long.divides(value: Long) = value % this == 0L