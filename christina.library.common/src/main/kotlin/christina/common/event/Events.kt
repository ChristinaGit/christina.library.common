package christina.common.event

import christina.common.event.basic.BasicEvent
import christina.common.event.core.InternalEvent
import christina.common.event.core.NoticeInternalEvent

object Events {
    @JvmStatic
    fun <EventData> basic(): InternalEvent<EventData> = BasicEvent()

    @JvmStatic
    fun notice(): NoticeInternalEvent = BasicEvent()
}