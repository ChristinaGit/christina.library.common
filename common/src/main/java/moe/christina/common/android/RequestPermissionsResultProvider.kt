package moe.christina.common.android

import moe.christina.common.android.event.RequestPermissionsResultEventData
import moe.christina.common.event.Event

interface RequestPermissionsResultProvider {
    val onRequestPermissionsResult: Event<RequestPermissionsResultEventData>
}