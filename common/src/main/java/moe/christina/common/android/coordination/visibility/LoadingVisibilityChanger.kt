package moe.christina.common.android.coordination.visibility

import android.support.annotation.CallSuper
import android.view.View
import moe.christina.common.android.view.ContentLoaderProgressBar

open class LoadingVisibilityChanger
@JvmOverloads
constructor(
    invisibleVisibility: Int = VisibilityChanger.DEFAULT_INVISIBLE_VISIBILITY
) : VisibilityChanger(invisibleVisibility) {
    @CallSuper
    override fun changeState(target: View, state: Boolean) {
        if (target is ContentLoaderProgressBar) {
            if (state) {
                target.show()
            } else {
                target.hide()
            }
        } else {
            super.changeState(target, state)
        }
    }
}