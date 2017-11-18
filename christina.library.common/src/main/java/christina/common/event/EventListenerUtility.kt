package christina.common.event

typealias NoticeEventListener = EventListener<Unit>

fun <TEventData> eventListener(onEvent: (TEventData) -> Unit): EventListener<TEventData> =
    object : EventListener<TEventData> {
        override fun onEvent(eventData: TEventData) = onEvent(eventData)
    }

fun eventListener(onEvent: () -> Unit): NoticeEventListener =
    object : EventListener<Unit> {
        override fun onEvent(eventData: Unit) = onEvent()
    }