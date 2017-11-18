package christina.common.event

@FunctionalInterface
interface EventListener<in TEventData> {
    fun onEvent(eventData: TEventData)
}