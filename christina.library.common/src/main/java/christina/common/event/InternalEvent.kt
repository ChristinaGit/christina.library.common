package christina.common.event

interface InternalEvent<TEventData> : Event<TEventData> {
    fun rise(eventData: TEventData)
}