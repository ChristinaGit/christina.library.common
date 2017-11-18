package christina.common.event

operator fun <TEventData> Event<TEventData>.plusAssign(listener: EventListener<TEventData>) = addListener(listener)

operator fun <TEventData> Event<TEventData>.minusAssign(listener: EventListener<TEventData>) = removeListener(listener)