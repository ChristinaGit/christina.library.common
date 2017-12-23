package christina.common.event.core

@FunctionalInterface
interface EventListener<in EventData> {
    fun onEvent(eventData: EventData)
}