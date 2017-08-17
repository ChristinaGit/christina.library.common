package moe.christina.common.android.event

import android.content.Intent
import moe.christina.common.core.event.Event

open class ActivityResultEvent(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent) : Event()