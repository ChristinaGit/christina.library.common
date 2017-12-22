package christina.common.event.core

interface Event<out TEventData> {
    fun addListener(listener: EventListener<TEventData>)
    fun removeListener(listener: EventListener<TEventData>)
}