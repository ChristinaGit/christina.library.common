package moe.christina.common.android.event

import android.content.Intent

open class ActivityResultEventData(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent)