package moe.christina.common.event

interface Event<out TEventData> {
    fun addListener(listener: EventListener<TEventData>)
    fun removeListener(listener: EventListener<TEventData>)
}