package christina.common.event.core

interface InternalEvent<EventData> : Event<EventData> {
    fun rise(eventData: EventData)
}