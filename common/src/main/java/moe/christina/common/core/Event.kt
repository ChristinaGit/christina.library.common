package moe.christina.common.core

open class Event {
    companion object {
        @JvmStatic
        val empty: Event by lazy(LazyThreadSafetyMode.PUBLICATION, ::Event)
    }
}