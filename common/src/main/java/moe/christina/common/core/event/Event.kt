package moe.christina.common.core.event

open class Event {
    companion object {
        @JvmStatic
        private val EMPTY = Event()

        @JvmStatic
        fun empty() = EMPTY
    }
}