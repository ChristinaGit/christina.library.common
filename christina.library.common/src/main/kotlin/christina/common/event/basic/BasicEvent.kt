package christina.common.event.basic

import android.support.annotation.CallSuper
import christina.common.event.core.EventListener
import christina.common.event.core.InternalEvent
import java.util.concurrent.CopyOnWriteArrayList

open class BasicEvent<EventData> : InternalEvent<EventData> {
    protected val listeners: MutableList<EventListener<EventData>> = CopyOnWriteArrayList()

    @CallSuper
    override fun rise(eventData: EventData) {
        listeners.forEach { it.onEvent(eventData) }
    }

    @CallSuper
    override fun addListener(listener: EventListener<EventData>) {
        listeners += listener
    }

    @CallSuper
    override fun removeListener(listener: EventListener<EventData>) {
        listeners -= listener
    }
}