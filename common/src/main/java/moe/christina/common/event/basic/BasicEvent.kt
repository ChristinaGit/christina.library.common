package moe.christina.common.event.basic

import android.support.annotation.CallSuper
import moe.christina.common.event.EventListener
import moe.christina.common.event.InternalEvent
import java.util.LinkedList

open class BasicEvent<TEventData> : InternalEvent<TEventData> {
    protected val listeners: MutableList<EventListener<TEventData>> = LinkedList()

    @CallSuper
    override fun rise(eventData: TEventData) {
        listeners.forEach { it.onEvent(eventData) }
    }

    @CallSuper
    override fun addListener(listener: EventListener<TEventData>) {
        listeners += listener
    }

    @CallSuper
    override fun removeListener(listener: EventListener<TEventData>) {
        listeners -= listener
    }
}