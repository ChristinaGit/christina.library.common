package moe.christina.common.android.event

import android.content.Intent
import moe.christina.common.core.Event

open class ActivityResultEvent(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent) : Event()