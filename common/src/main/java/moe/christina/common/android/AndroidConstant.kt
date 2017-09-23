package moe.christina.common.android

object AndroidConstant {
    inline fun <reified T> getSavedStateKey(key: String) = "${T::class.qualifiedName}:$key"
}