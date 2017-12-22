package christina.common.event.core

typealias NoticeEventListener = EventListener<Unit>

inline fun <TEventData> eventListener(crossinline onEvent: (TEventData) -> Unit): EventListener<TEventData> =
    object : EventListener<TEventData> {
        override fun onEvent(eventData: TEventData) = onEvent(eventData)
    }

inline fun eventListener(crossinline onEvent: () -> Unit): EventListener<Unit> =
    object : EventListener<Unit> {
        override fun onEvent(eventData: Unit) = onEvent()
    }