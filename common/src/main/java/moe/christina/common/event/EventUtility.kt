package moe.christina.common.event

typealias NoticeEvent = Event<Unit>
typealias NoticeEventListener = EventListener<Unit>
typealias NoticeInternalEvent = InternalEvent<Unit>

fun <TEventData> eventListener(lambda: (TEventData) -> Unit): EventListener<TEventData> =
    object : EventListener<TEventData> {
        override fun onEvent(eventData: TEventData) = lambda(eventData)
    }

fun eventListener(lambda: () -> Unit): NoticeEventListener =
    object : NoticeEventListener {
        override fun onEvent(eventData: Unit) = lambda()
    }

fun InternalEvent<Unit>.rise() = rise(Unit)

operator fun <TEventData> InternalEvent<TEventData>.invoke(eventData: TEventData) = rise(eventData)

operator fun InternalEvent<Unit>.invoke() = rise()

operator fun <TEventData> Event<TEventData>.plusAssign(listener: EventListener<TEventData>) = addListener(listener)

operator fun <TEventData> Event<TEventData>.minusAssign(listener: EventListener<TEventData>) = removeListener(listener)
