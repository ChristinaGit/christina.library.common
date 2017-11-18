package christina.common.event

import christina.common.event.basic.BasicEvent

object Events {
    fun <TEventData> basic(): InternalEvent<TEventData> = BasicEvent()
}