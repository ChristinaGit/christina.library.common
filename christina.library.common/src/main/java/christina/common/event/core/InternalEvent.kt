package christina.common.event.core

interface InternalEvent<TEventData> : Event<TEventData> {
    fun rise(eventData: TEventData)
}