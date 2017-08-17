package moe.christina.common.core.event

open class Event {
    companion object {
        @JvmStatic
        val empty: Event by lazy(LazyThreadSafetyMode.PUBLICATION, ::Event)
    }
}