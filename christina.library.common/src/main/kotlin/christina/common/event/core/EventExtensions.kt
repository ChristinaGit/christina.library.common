package christina.common.event.core

operator fun <EventData> Event<EventData>.plusAssign(listener: EventListener<EventData>) = addListener(listener)

operator fun <EventData> Event<EventData>.minusAssign(listener: EventListener<EventData>) = removeListener(listener)