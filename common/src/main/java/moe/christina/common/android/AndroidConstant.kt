package moe.christina.common.android

object AndroidConstant {
    inline fun <reified TActivity> savedStateKey(key: String) = TActivity::class.qualifiedName + key
    inline fun <reified TActivity> logTag() = TActivity::class.qualifiedName
}