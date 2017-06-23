package moe.christina.common.android.event

import java.util.Arrays
import java.util.Objects

data class RequestPermissionsResultEvent(
        val requestCode: Int,
        val permissions: Array<String>,
        var grantResults: IntArray) {
    override fun equals(other: Any?): Boolean {
        if (other !is RequestPermissionsResultEvent)
            return false

        return requestCode == other.requestCode
                && Arrays.equals(permissions, other.permissions)
                && Arrays.equals(grantResults, other.grantResults)
    }

    override fun hashCode(): Int {
        return Objects.hash(
                requestCode,
                Arrays.hashCode(permissions),
                Arrays.hashCode(grantResults))
    }
}