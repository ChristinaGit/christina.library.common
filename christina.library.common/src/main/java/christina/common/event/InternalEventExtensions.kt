package christina.common.event

fun InternalEvent<Unit>.rise() = rise(Unit)

operator fun <TEventData> InternalEvent<TEventData>.invoke(eventData: TEventData) = rise(eventData)

operator fun InternalEvent<Unit>.invoke() = invoke(Unit)