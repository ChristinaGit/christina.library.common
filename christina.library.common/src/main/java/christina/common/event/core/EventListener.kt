package christina.common.event.core

@FunctionalInterface
interface EventListener<in TEventData> {
    fun onEvent(eventData: TEventData)
}