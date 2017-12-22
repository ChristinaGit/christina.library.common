package christina.common.event

import christina.common.event.basic.BasicEvent
import christina.common.event.core.InternalEvent

object Events {
    @JvmStatic
    fun <TEventData> basic(): InternalEvent<TEventData> = BasicEvent()
}