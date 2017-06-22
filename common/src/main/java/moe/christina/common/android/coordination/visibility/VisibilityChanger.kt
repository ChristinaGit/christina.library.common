package moe.christina.common.android.coordination.visibility

import android.view.View

interface VisibilityChanger {
    fun changeVisibility(view: View, visible: Boolean)
}
