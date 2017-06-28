package moe.christina.common.android.event

import moe.christina.common.core.Event

open class RequestPermissionsResultEvent(
        val requestCode: Int,
        val permissions: List<String>,
        val grantResults: List<Int>)
    : Event()