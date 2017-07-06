package moe.christina.common.android.coordination.visibility

import android.support.annotation.CallSuper
import android.view.View

open class SimpleVisibilityChanger(
    val hideVisibility: Int = SimpleVisibilityChanger.DEFAULT_HIDE_VISIBILITY)
    : VisibilityChanger {
    companion object {
        @JvmStatic
        val DEFAULT_HIDE_VISIBILITY: Int = View.GONE;
    }

    @CallSuper
    override fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else hideVisibility
    }
}
