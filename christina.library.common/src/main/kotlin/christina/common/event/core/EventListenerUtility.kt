package christina.common.event.core

typealias NoticeEventListener = EventListener<Unit>

inline fun <EventData> eventListener(crossinline onEvent: (EventData) -> Unit): EventListener<EventData> =
    object : EventListener<EventData> {
        override fun onEvent(eventData: EventData) = onEvent(eventData)
    }

inline fun eventListener(crossinline onEvent: () -> Unit): EventListener<Unit> =
    object : EventListener<Unit> {
        override fun onEvent(eventData: Unit) = onEvent()
    }