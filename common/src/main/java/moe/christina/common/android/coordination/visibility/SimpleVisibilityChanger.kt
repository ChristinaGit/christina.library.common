package moe.christina.common.android.coordination.visibility

import android.view.View

open class SimpleVisibilityChanger(private val hideVisibility: Int = DEFAULT_HIDE_VISIBILITY) : VisibilityChanger {
    companion object {
        val DEFAULT_HIDE_VISIBILITY: Int = View.GONE;
    }

    @Override
    override fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else hideVisibility
    }
}
