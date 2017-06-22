package moe.christina.common.android

import io.reactivex.Observable
import moe.christina.common.android.event.RequestPermissionsResultEvent

interface RequestPermissionsResultProvider {
    val onRequestPermissionsResult: Observable<RequestPermissionsResultEvent>
}