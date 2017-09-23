package moe.christina.common.event

import moe.christina.common.event.basic.BasicEvent

object Events {
    fun <TEventData> basic(): InternalEvent<TEventData> = BasicEvent()
}