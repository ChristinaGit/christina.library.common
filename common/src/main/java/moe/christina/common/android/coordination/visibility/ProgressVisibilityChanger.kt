package moe.christina.common.android.coordination.visibility

import android.support.annotation.CallSuper
import android.view.View
import moe.christina.common.android.view.ContentLoaderProgressBar

open class ProgressVisibilityChanger(
    hideVisibility: Int = SimpleVisibilityChanger.DEFAULT_HIDE_VISIBILITY)
    : SimpleVisibilityChanger(hideVisibility) {
    @CallSuper
    override fun changeVisibility(view: View, visible: Boolean) {
        if (view is ContentLoaderProgressBar) {
            if (visible) {
                view.show()
            } else {
                view.hide()
            }
        } else {
            super.changeVisibility(view, visible)
        }
    }
}