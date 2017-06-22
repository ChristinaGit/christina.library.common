package moe.christina.common.android.event

import android.content.Intent

data class ActivityResultEvent(val requestCode: Int, val resultCode: Int, var data: Intent)