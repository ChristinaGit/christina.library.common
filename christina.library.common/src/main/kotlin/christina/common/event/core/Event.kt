package christina.common.event.core

interface Event<out EventData> {
    fun addListener(listener: EventListener<EventData>)
    fun removeListener(listener: EventListener<EventData>)
}