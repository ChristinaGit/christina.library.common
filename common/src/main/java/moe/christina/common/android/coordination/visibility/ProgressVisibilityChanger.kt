package moe.christina.common.android.coordination.visibility

import android.view.View
import moe.christina.common.android.view.ContentLoaderProgressBar

class ProgressVisibilityChanger(hideVisibility: Int = DEFAULT_HIDE_VISIBILITY)
    : SimpleVisibilityChanger(hideVisibility) {
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
