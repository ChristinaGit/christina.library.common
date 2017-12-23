package christina.common.event.core

fun InternalEvent<Unit>.rise() = rise(Unit)

operator fun <EventData> InternalEvent<EventData>.invoke(eventData: EventData) = rise(eventData)

operator fun InternalEvent<Unit>.invoke() = invoke(Unit)