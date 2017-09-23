package moe.christina.common.android.event

open class RequestPermissionsResultEventData(
    val requestCode: Int,
    val permissions: List<String>,
    val grantResults: List<Int>)