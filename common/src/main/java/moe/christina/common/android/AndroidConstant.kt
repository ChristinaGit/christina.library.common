package moe.christina.common.android

object AndroidConstant {
    inline fun <reified T> savedStateKey(key: String) = "${T::class.qualifiedName}:$key"
    inline fun <reified T> logTag() = T::class.qualifiedName
}